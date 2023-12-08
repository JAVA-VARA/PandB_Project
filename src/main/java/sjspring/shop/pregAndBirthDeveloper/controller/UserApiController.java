package sjspring.shop.pregAndBirthDeveloper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.AddUserRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateUserRequest;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    @PostMapping("/users")
    public String signup(AddUserRequest request){
        userService.save(request);
        return "redirect:/login";
    }

    @PutMapping("/update/users")
    public String updateInfo(@RequestBody UpdateUserRequest request, Principal principal){
        String userEmail = principal.getName();
        userService.update(request, userEmail);
        return "/myPage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @DeleteMapping("/delete/users")
    public ResponseEntity<Void> deleteUser(Principal principal){
        userService.delete(principal);

        return ResponseEntity.ok()
                .build();
    }

}
