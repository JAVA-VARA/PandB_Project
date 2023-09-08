package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long views;

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .views(views)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .views(views)
                .build();

    }
}
