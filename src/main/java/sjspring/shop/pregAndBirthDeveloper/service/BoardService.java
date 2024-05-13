package sjspring.shop.pregAndBirthDeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sjspring.shop.pregAndBirthDeveloper.domain.*;
import sjspring.shop.pregAndBirthDeveloper.dto.*;
import sjspring.shop.pregAndBirthDeveloper.repository.*;
import sjspring.shop.pregAndBirthDeveloper.util.LocalUploadUtil;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final AttachedFileRepository attachedFileRepository;
    private final UserRepository userRepository;
    private final ScrapArticlesRepository scrapArticlesRepository;

    public Board save(AddArticleRequest request, String userName) throws IOException, InterruptedException {


        List<AttachedFileDto> attachedFileDtos = new ArrayList<>();

        //첨부파일 저장.
        if(request.getFile() != null && !request.getFile().isEmpty()){
            LocalUploadUtil localUploadUtil = new LocalUploadUtil();

            for(MultipartFile file : request.getFile()){
                AttachedFileDto attachedFileDto = localUploadUtil.fileUploadToLocalDir(file);
                attachedFileDtos.add(attachedFileDto);
            }
        }

        //글 저장
        Board board = request.toEntity(userName);
        request.getBoardCategory().mappingBoard(board);

        for(AttachedFileDto attachedFileDto : attachedFileDtos){

            attachedFileDto.setBoard(board);
            attachedFileRepository.save(attachedFileDto.toEntity());

            AttachedFile attachedFile = attachedFileRepository.findAttachedFileByFileName(attachedFileDto.getFileName());
            board.mappingAttachedFileToBoard(attachedFile);
        }

        return boardRepository.save(board);
    }

    //ReadAll
    public List<Board> findAll() {
        return boardRepository.findAll();
    }


    public Page<ArticleViewResponse> getBoardList(Long  category_id, Pageable pageable){

        Page<Board> boardList;

        if(category_id ==4){
            boardList = boardRepository.findByViewsGreaterThanEqual(10, pageable);
        }else {
            boardList = boardRepository.findByCategory_Id(category_id, pageable);
        }

        return boardList.map(this::convertToDto);

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

        Page<Board> boardList = boardRepository.findByTitleContaining(searchKeyword, pageable);

        return boardList.map(this::convertToDto);
    }

    //삭제
    public void delete(long boardId){
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(()-> new IllegalArgumentException("NOT FOUND :" + boardId));

        String categoryName = board.getCategory().getCategoryName();
        BoardCategory boardCategory = boardCategoryRepository.findByCategoryName(categoryName);

        authorizeArticleAuthor(board);

        boardCategory.deleteBoard(board);
        boardRepository.deleteById(boardId);

    }

    //수정
    @Transactional
    public Board update(long boardId, UpdateArticleRequest request) throws IOException, InterruptedException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("not found:" + boardId));

        authorizeArticleAuthor(board);

        BoardCategory boardCategory = (BoardCategory) boardCategoryRepository.findByCategoryName(request.getCategory());

        //첨부파일 저장.
        List<AttachedFileDto> attachedFileDtos = new ArrayList<>();
        if(request.getFiles() != null && !request.getFiles().isEmpty()){
            LocalUploadUtil localUploadUtil = new LocalUploadUtil();

            for(MultipartFile file : request.getFiles()){
                AttachedFileDto attachedFileDto = localUploadUtil.fileUploadToLocalDir(file);
                attachedFileDtos.add(attachedFileDto);
            }
            for(AttachedFileDto attachedFileDto : attachedFileDtos){

                attachedFileDto.setBoard(board);
                attachedFileRepository.save(attachedFileDto.toEntity());

                AttachedFile attachedFile = attachedFileRepository.findAttachedFileByFileName(attachedFileDto.getFileName());
                board.mappingAttachedFileToBoard(attachedFile);
            }
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

    public ScrapArticle scrap(long boardId, String currentUserEmail){

        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(()-> new IllegalArgumentException("not found:"));

        Board board = boardRepository.findByBoardNo(boardId)
                .orElseThrow(()-> new IllegalArgumentException("not found:"));

        //이미 스크랩한 글인 경우 알림
        for(ScrapArticle scrapArticle : user.getScrapedArticles()){
            if(scrapArticle.getBoard().getBoardNo() == boardId){
                throw new RuntimeException("이미 저장되어 있는 게시글");
            }
        }

        ScrapRequestDto scrapRequestDto = new ScrapRequestDto(user, board);
        ScrapArticle scrapArticle = scrapRequestDto.toEntity();

        return scrapArticlesRepository.save(scrapArticle);
    }

    public List<Comment> findCommentListByBoardNo(Long boardNo){
        return boardRepository.findCommentListByBoardNo(boardNo);
    }
}
