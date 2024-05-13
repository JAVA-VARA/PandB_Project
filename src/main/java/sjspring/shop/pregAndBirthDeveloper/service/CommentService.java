package sjspring.shop.pregAndBirthDeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.CommentRequestDto;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateCommentRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.CommentRepository;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Comment addComment(CommentRequestDto commentRequestDto){

        //comment 저장
        Comment comment = commentRequestDto.toEntity();

        //user와 매핑
        commentRequestDto.getUser().mappingCommentToUser(comment);

        //board와 매핑
        Board board = boardRepository.findByBoardNo(commentRequestDto.getBoard().getBoardNo())
                .orElseThrow(() -> new RuntimeException("Board not found"));

        board.mappingCommentToBoard(comment);

        return commentRepository.save(comment);
    }

    public void update(long id, UpdateCommentRequest request){
        Comment comment = commentRepository.findCommentById(id);

        comment.update(request.getContent());
        commentRepository.save(comment);
    }

    public void delete(long id){
        Comment comment = commentRepository.findCommentById(id);

        User user = comment.getUser();
        Board board = comment.getBoard();

        user.deleteCommentInUser(comment);
        board.deleteCommentInBoard(comment);

        commentRepository.deleteById(id);
    }

}
