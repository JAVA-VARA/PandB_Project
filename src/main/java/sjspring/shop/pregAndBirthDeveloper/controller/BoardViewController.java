package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.dto.ArticleViewResponse;
import sjspring.shop.pregAndBirthDeveloper.dto.BoardListViewResponse;
import sjspring.shop.pregAndBirthDeveloper.dto.ResponseDto;
import sjspring.shop.pregAndBirthDeveloper.service.BoardService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping({"/", "/homePage"})
    public String home(Model model){
        List<BoardListViewResponse> articles = boardService.findAll().stream()
                .map(BoardListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);

        return "homePage";
    }


    //자유게시판 viewList 수정하기전!!!!!
//    @GetMapping("/articles")
//    public String getArticles(Model model){
//        List<BoardListViewResponse> articles = boardService.findAll().stream()
//                .map(BoardListViewResponse::new)
//                .toList();
//        model.addAttribute("articles", articles);
//
//        return "freeBoardList";
//    }

    //자유게시판 viewList 수정후!!!//
    @GetMapping("/articles")
    public String  getAllBoardList(
            @PageableDefault(sort = "boardNo",direction = Sort.Direction.DESC) Pageable pageable,
            Model model)
    {
        Page<ArticleViewResponse> boardPage = boardService.getBoardList(pageable);
        model.addAttribute("boardPage",boardPage);
        return "/freeBoardList";
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
