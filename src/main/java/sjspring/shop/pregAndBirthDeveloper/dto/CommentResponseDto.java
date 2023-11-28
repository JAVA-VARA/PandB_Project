package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.*;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;
import sjspring.shop.pregAndBirthDeveloper.domain.User;


@Data
public class CommentResponseDto {
    private String content;
    private Board board;
    private User user;

    public CommentResponseDto(Comment comment){
        this.content = comment.getContent();
        this.board = comment.getBoard();
        this.user = comment.getUser();
    }
}
