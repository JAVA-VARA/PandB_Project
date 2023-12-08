package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.*;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.ScrapArticle;
import sjspring.shop.pregAndBirthDeveloper.domain.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScrapRequestDto {
    private User user;
    private Board board;

    public ScrapArticle toEntity(){
        return   ScrapArticle.builder()
                .user(user)
                .board(board)
                .build();

    }
}
