package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.User;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyArticlesDto {
    private User user;
    private List<Board> boardList;

    public MyArticlesDto(User user, List<Board> boardList){
        this.user = user;
        this.boardList = boardList;
    }


}
