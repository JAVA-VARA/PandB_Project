package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.*;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;
import sjspring.shop.pregAndBirthDeveloper.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
    private String content;
    private Board board;
    private User user;


    @Builder
    public Comment toEntity(){
        Comment comment = Comment.builder()
                .content(content)
                .board(board)
                .user(user)
                .build();

        return comment;
    }

}
