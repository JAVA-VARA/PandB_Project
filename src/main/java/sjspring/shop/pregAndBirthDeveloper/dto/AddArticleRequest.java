package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddArticleRequest {
    private String title;
    private String content;
    private String author;
    private int views;

    public Board toEntity(String username){
        return Board.builder()
                .title(title)
                .content(content)
                .views(views)
                .author(author)
                .email(username)
                .build();
    }
}
