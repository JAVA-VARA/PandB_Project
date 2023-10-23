package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ArticleViewResponse {
    private Long boardNo;
    private String title;
    private String content;
    private String author;
    private int views;
    private BoardCategory category;
    private LocalDateTime createdAt;



    public ArticleViewResponse(Board board){
        this.boardNo = board.getBoardNo();
        this.title = board.getTitle();
        this.author = board.getAuthor();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.createdAt = board.getCreatedAt();
        this.views = board.getViews();
    }
}
