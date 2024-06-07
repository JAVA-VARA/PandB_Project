package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;
import sjspring.shop.pregAndBirthDeveloper.domain.ScrapArticle;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.MyArticlesDto;
import sjspring.shop.pregAndBirthDeveloper.dto.MyCommentsDto;
import sjspring.shop.pregAndBirthDeveloper.dto.MyPageResponse;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MyArticlesViewController {
    private final UserService userService;

    @GetMapping("/mypage/my-articles")
    public String myArticlesViewer(Model model, Principal principal){

        if(principal != null){
            String userEmail = principal.getName();
            User user = userService.findByEmail(userEmail);
            List<Board> boardList = user.getBoardList();

            MyArticlesDto myArticlesDto = new MyArticlesDto(user, boardList);
            model.addAttribute("myArticles" , myArticlesDto);

            return "myArticles";
        }
        else {
            return "redirect:/show-login-popup";
        }

    }

    @GetMapping("/mypage/my-comments")
    public String myCommentsViewer(Model model, Principal principal){
        if(principal != null) {
            String userEmail = principal.getName();
            User user = userService.findByEmail(userEmail);
            List<Comment> commentList = user.getCommentList();

            MyCommentsDto myCommentsDto = new MyCommentsDto(user, commentList);
            model.addAttribute("myComments", myCommentsDto);

            return "myComments";
        }
        else {
            return "redirect:/show-login-popup";
        }
    }

    @GetMapping("/mypage/my-info")
    public String myInformation(Model model, Principal principal){
        if(principal != null) {
            String userEmail = principal.getName();
            User user = userService.findByEmail(userEmail);

            MyCommentsDto myCommentsDto = new MyCommentsDto(user);
            model.addAttribute("myInfo", myCommentsDto);

            return "myPage";
        }
        else {
            return "redirect:/show-login-popup";
        }
    }

    @GetMapping("/mypage/my-scraps")
    public String myScrapViewer(Model model, Principal principal){
        if(principal != null) {
            String userEmail = principal.getName();
            User user = userService.findByEmail(userEmail);

            String nickname = user.getNickname();
            int numberOfArticles = user.getBoardList().size();
            int numberOfComments = user.getCommentList().size();
            List<ScrapArticle> scrapArticles = user.getScrapedArticles();

            MyPageResponse myPageResponse = new MyPageResponse(nickname, numberOfArticles, numberOfComments, scrapArticles);

            model.addAttribute("myPageResponse", myPageResponse);

            return "myScrapedArticles";
        }else {
            return "redirect:/show-login-popup";
        }

    }

    @GetMapping("/show-login-popup")
    public String showLoginPopup() {
        return "loginPopup";
    }
}
