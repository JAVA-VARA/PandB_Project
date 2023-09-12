package sjspring.shop.pregAndBirthDeveloper.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sjspring.shop.pregAndBirthDeveloper.dto.AddUserRequest;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;
import org.springframework.stereotype.Controller;

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
}
