package sjspring.shop.pregAndBirthDeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.dto.AddArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BoardRepository boardRepository;

    //Create
    public Board save(AddArticleRequest request){
        return boardRepository.save(request.toEntity());
    }

    //ReadAll
    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    //ReadOne
    public Board findById(long boardId){
        return boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("not found:" + boardId));
    }

    public void delete(long boardId){
        boardRepository.deleteById(boardId);
    }

    @Transactional
    public Board update(long boardId, UpdateArticleRequest request){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("not found:" + boardId));

        board.update(request.getTitle(), request.getContent());

        return board;
    }
}
