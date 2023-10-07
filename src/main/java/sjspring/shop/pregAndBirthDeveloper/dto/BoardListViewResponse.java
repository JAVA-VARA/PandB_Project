package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import java.time.LocalDateTime;

@Getter
public class BoardListViewResponse {
    private final Long boardNo;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    private final Long views;

    public BoardListViewResponse(Board board){
        this.boardNo = board.getBoardNo();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
        this.createdAt = board.getCreatedAt();
        this.views = board.getViews();

    }

}
