package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;

@Getter
public class FindArticle {
    private final String title;
    private final String content;

    public FindArticle(Board board){
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
