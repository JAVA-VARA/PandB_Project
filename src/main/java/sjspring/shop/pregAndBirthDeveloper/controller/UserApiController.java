package sjspring.shop.pregAndBirthDeveloper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import sjspring.shop.pregAndBirthDeveloper.Validator.CheckEmailValidator;
import sjspring.shop.pregAndBirthDeveloper.dto.AddUserRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateUserRequest;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;
    private final CheckEmailValidator checkEmailValidator;

    //유효성 검증
    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(checkEmailValidator);
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, String>> signup(
            @Valid
            @RequestBody AddUserRequest request,
            Errors errors, Model model)  {

        //회원가입 실패 시 입력했던 정보 유지, error 문구 안내
        if (errors.hasErrors()) {
            model.addAttribute("dto", request);

            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return ResponseEntity.status(400).body(validatorResult);
        }

        userService.save(request);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/update/users")
    public ResponseEntity<Map<String, String>> updateInfo(
            @RequestBody UpdateUserRequest request,
            Principal principal){

        String userEmail = principal.getName();
        userService.update(request, userEmail);

        return ResponseEntity.ok()
                .build();
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
