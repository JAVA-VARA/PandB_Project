package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.dto.ArticleViewResponse;
import sjspring.shop.pregAndBirthDeveloper.dto.BoardListViewResponse;
import sjspring.shop.pregAndBirthDeveloper.service.BoardService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping("/articles")
    public String getArticles(Model model){
        List<BoardListViewResponse> articles = boardService.findAll().stream()
                .map(BoardListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);

        if(articles.isEmpty()){
            return "freeBoardListEmpty";
        }

        return "freeBoardList";
    }

    @GetMapping("/articles/{board_no}")
    public String getArticle(@PathVariable Long board_no, Model model){
        Board board = boardService.findById(board_no);
        model.addAttribute("board", new ArticleViewResponse(board));

        return "freeBoard";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long board_no, Model model){
        System.out.println(board_no);
        if(board_no == null){
            model.addAttribute("article", new ArticleViewResponse());
        }else{
            Board board = boardService.findById(board_no);
            model.addAttribute("article", new ArticleViewResponse(board));
        }

        return "newArticle";
    }

}
