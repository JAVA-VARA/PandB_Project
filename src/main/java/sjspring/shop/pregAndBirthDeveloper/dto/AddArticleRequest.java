package sjspring.shop.pregAndBirthDeveloper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddArticleRequest {
    private String title;
    private String content;
    private String author;
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

        boardCategory.mappingBoard(board);
        return board;
    }
}
