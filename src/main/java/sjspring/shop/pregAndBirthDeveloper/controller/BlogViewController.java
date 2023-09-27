package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.dto.ArticleListViewResponse;
import sjspring.shop.pregAndBirthDeveloper.dto.ArticleViewResponse;
import sjspring.shop.pregAndBirthDeveloper.service.BlogService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

//    @GetMapping("/articles")
//    public String getArticles(Model model){
//        List<ArticleListViewResponse> articles = blogService.findAll().stream()
//                .map(ArticleListViewResponse::new)
//                .toList();
//        model.addAttribute("articles", articles);
//
//        return "articleList";
//    }

    @GetMapping("/articles")
    public String getArticles(){
        return "articleList";
    }




//    @GetMapping("/articles/{board_no}")
//    public String getArticle(@PathVariable Long board_no, Model model){
//        Board board = blogService.findById(board_no);
//        model.addAttribute("board", new ArticleViewResponse(board));
//
//        return "board";
//    }

}
