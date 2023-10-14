package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Data;
import lombok.Getter;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;

import java.time.LocalDateTime;

@Data
public class FindArticle {
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    private final int views;

    public FindArticle(Board board){
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
        this.createdAt=board.getCreatedAt();
        this.views=board.getViews();
    }
}
