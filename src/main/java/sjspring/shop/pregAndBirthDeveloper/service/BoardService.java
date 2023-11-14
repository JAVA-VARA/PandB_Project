package sjspring.shop.pregAndBirthDeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;
import sjspring.shop.pregAndBirthDeveloper.dto.AddArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.ArticleViewResponse;
import sjspring.shop.pregAndBirthDeveloper.dto.AttachedFileDto;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.AttachedFileRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardCategoryRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardRepository;
import sjspring.shop.pregAndBirthDeveloper.util.UploadFileUtil;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final AttachedFileRepository attachedFileRepository;

    public Board save(AddArticleRequest request, String userName) throws IOException {

        //첨부파일 저장.
        if(request.getFile() != null){
            UploadFileUtil uploadFileUtil = new UploadFileUtil();
            AttachedFileDto attachedFileDto = uploadFileUtil.uploadFile(request.getFile());
            attachedFileRepository.save(attachedFileDto.toEntity());
        }

        //글 저장
        Board board = request.toEntity(userName);
        request.getBoardCategory().mappingBoard(board);

        return boardRepository.save(board);
    }

    //ReadAll
    public List<Board> findAll() {
        return boardRepository.findAll();
    }


    public Page<ArticleViewResponse> getBoardList(Pageable pageable){
        int page = (pageable.getPageNumber()==0)? 0 : (pageable.getPageNumber() - 1);
        Page<Board> boardList = boardRepository.findAll(pageable);
        Page<ArticleViewResponse> pageList = boardList.map(this::convertToDto);

        return pageList;

    }


    private ArticleViewResponse convertToDto(Board board){
        ArticleViewResponse articleViewResponse = new ArticleViewResponse();
        BeanUtils.copyProperties(board, articleViewResponse);

        return articleViewResponse;
    }

    //ReadOne
    public Board findById(long boardId){
        return boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("not found:" + boardId));
    }

    //Searching
    public Page<ArticleViewResponse> boardSearchList(String searchKeyword, Pageable pageable){
        int page = (pageable.getPageNumber()==0)? 0 : (pageable.getPageNumber() - 1);

        Page<Board> boardList = boardRepository.findByTitleContaining(searchKeyword, pageable);
        Page<ArticleViewResponse> pageList = boardList.map(this::convertToDto);

        return pageList;
    }

    public void delete(long boardId){
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(()-> new IllegalArgumentException("NOT FOUNd :" + boardId));

        String categoryName = board.getCategory().getCategoryName();
        BoardCategory boardCategory = boardCategoryRepository.findByCategoryName(categoryName);

        authorizeArticleAuthor(board);

        boardCategory.deleteBoard(board);
        boardRepository.deleteById(boardId);

    }

    @Transactional
    public Board update(long boardId, UpdateArticleRequest request) throws IOException {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("not found:" + boardId));

        authorizeArticleAuthor(board);

        BoardCategory boardCategory = (BoardCategory) boardCategoryRepository.findByCategoryName(request.getCategory());
        if(boardCategory == null){

            boardCategory = BoardCategory.builder()
                    .categoryName(request.getCategory())
                    .build();

            boardCategoryRepository.save(boardCategory);
            board.update(request.getTitle(), request.getContent(), boardCategory);
            boardCategory.mappingBoard(board);


            return board;

        }

        //첨부파일 저장.
        if(request.getFiles() != null){
            UploadFileUtil uploadFileUtil = new UploadFileUtil();
            AttachedFileDto attachedFileDto = uploadFileUtil.uploadFile(request.getFiles());
            attachedFileRepository.save(attachedFileDto.toEntity());
        }

        board.update(request.getTitle(), request.getContent(), boardCategory);
        return board;
    }

    @Transactional
    public void updateView(long boardId, int view){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("not found:" + boardId));

        board.update(view);
    }

    private static void authorizeArticleAuthor(Board board){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!board.getEmail().equals(userName)){
            throw new IllegalArgumentException("not authorized");
        }
    }
}
