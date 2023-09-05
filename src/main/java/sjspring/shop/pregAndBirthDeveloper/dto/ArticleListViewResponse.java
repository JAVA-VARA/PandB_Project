package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;

@Getter
public class ArticleListViewResponse {

    private final Long boardNo;
    private final String title;

    public ArticleListViewResponse(Board board){
        this.boardNo = board.getBoardNo();
        this.title = board.getTitle();
    }
}
