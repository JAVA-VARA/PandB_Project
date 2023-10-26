package sjspring.shop.pregAndBirthDeveloper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddArticleRequest {

    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    private String author;

    @NonNull
    private String category;

    private BoardCategory boardCategory;

    private int views;


    public Board toEntity(String username){
        Board board =  Board.builder()
                .title(title)
                .content(content)
                .views(views)
                .author(author)
                .email(username)
                .category(boardCategory)
                .build();

        return board;
    }
}
