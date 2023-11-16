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
import sjspring.shop.pregAndBirthDeveloper.service.BoardService;
import java.io.IOException;
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

    @GetMapping("/articles")
    public String  getAllBoardList(Model model,
                                   @PageableDefault(sort = "boardNo",direction = Sort.Direction.DESC) Pageable pageable,
                                   String searchKeyword){

        Page<ArticleViewResponse> list = null;
        if(searchKeyword == null){
            list = boardService.getBoardList(pageable);
        } else{
            list = boardService.boardSearchList(searchKeyword,pageable);
        }

        model.addAttribute("boardPage",list);

        return "/freeBoardList";
    }


    @GetMapping("/articles/{board_no}")
    public String getArticle(@PathVariable Long board_no, Model model) throws IOException {
        Board board = boardService.findById(board_no);

        ArticleViewResponse articleViewResponse = new ArticleViewResponse(board);
        model.addAttribute("board", articleViewResponse);

        //조회수
        int view = board.getViews();
        view = view + 1;
        boardService.updateView(board_no, view);

        return "freeBoard";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long board_no, Model model){
        if(board_no == null){
            model.addAttribute("article", new ArticleViewResponse());
        }else{
            Board board = boardService.findById(board_no);
            model.addAttribute("article", new ArticleViewResponse(board));
        }

        return "/newArticle";
    }

//    private byte[] readFileToByteArray(String filePath) throws IOException{
//        try(FileInputStream inputStream = new FileInputStream(filePath)){
//            return IOUtils.toByteArray(inputStream);
//        }
//    }
//
//
}
//    //첨부파일 테스트용~
//    @GetMapping("/fileAttachment")
//    public String newAttached(){
//        return "/fileAttachment";
//    }

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
