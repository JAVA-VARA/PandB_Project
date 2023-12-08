package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sjspring.shop.pregAndBirthDeveloper.domain.ScrapArticle;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MyPageResponse {
    private String nickname;
    private int numberOfArticles;
    private int numberOfComments;
    private List<ScrapArticle> scrapArticles;
}
