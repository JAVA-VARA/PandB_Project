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
    public Board findById(long board_id){
        return boardRepository.findById(board_id)
                .orElseThrow(()-> new IllegalArgumentException("not found:" + board_id));
    }

    public void delete(long board_id){
        boardRepository.deleteById(board_id);
    }

    @Transactional
    public Board update(long board_id, UpdateArticleRequest request){
        Board board = boardRepository.findById(board_id)
                .orElseThrow(()-> new IllegalArgumentException("not found:" + board_id));

        board.update(request.getTitle(), request.getContent());

        return board;
    }
}
