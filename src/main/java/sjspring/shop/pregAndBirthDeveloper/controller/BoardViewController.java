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

    //자유게시판 viewList
//    @GetMapping("/articles")
//    public String  getAllBoardList(
//            @PageableDefault(sort = "boardNo",direction = Sort.Direction.DESC) Pageable pageable,
//            Model model)
//    {
//        Page<ArticleViewResponse> boardPage = boardService.getBoardList(pageable);
//        model.addAttribute("boardPage",boardPage);
//        return "/freeBoardList";
//    }

    //자유게시판 viewList 수정후!!!//
    @GetMapping("/articles")
    public String  getAllBoardList(Model model,
                                   @PageableDefault(sort = "boardNo",direction = Sort.Direction.DESC) Pageable pageable,
                                   String searchKeyword){

        //검색 기능 추가
        Page<ArticleViewResponse> list = null;
        if(searchKeyword == null){
            list = boardService.getBoardList(pageable); //검색어가 없는 경우 기존의 list
        } else{
            list = boardService.boardSearchList(searchKeyword,pageable); //검색어가 있는 경우 검색 리스트 반환
        }

        model.addAttribute("boardPage",list);

        return "/freeBoardList";
    }


    @GetMapping("/articles/{board_no}")
    public String getArticle(@PathVariable Long board_no, Model model)
    {
        Board board = boardService.findById(board_no);
        ArticleViewResponse articleViewResponse = new ArticleViewResponse(board);

        //이미지 리턴 => ArticleViewResponse에 image 경로 추가하면 될듯?
        /*
         * 이미지 경로 불러오기
         * ArticleViewResponse에 삽입
         * html에서 board.filepath 불러오기.
         * */

        //이미지 경로 불러오기
        //String filePath = ~~~


        //파일 경로 저장
//        articleViewResponse.setFilePath(filePath);

        model.addAttribute("board", articleViewResponse);





        //조회수 기능 추가
        int view = board.getViews(); //현재 조회수 가져옴
        view = view + 1; //클릭시마다 조회수 1 증가
        boardService.updateView(board_no, view); //업데이트된 조회수 저장.

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

//    //첨부파일 테스트용~
//    @GetMapping("/fileAttachment")
//    public String newAttached(){
//        return "/fileAttachment";
//    }

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
