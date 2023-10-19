package sjspring.shop.pregAndBirthDeveloper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sjspring.shop.pregAndBirthDeveloper.dto.AddUserRequest;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;


    @PostMapping("/users")
    public String signup(AddUserRequest request){
        userService.save(request);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
    //여기서 부터 추가함(OAuth2 추가 정보 입력)
    @PostMapping("/oauth2/register")
    public void oauth2Authorize(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "hp", required = false) String hp) {
        userService.save(name, hp);
    }
}
