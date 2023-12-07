package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sjspring.shop.pregAndBirthDeveloper.dto.AddSignupInfoDto;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AdditionalInfoController {

    private final UserService userService;

    @GetMapping("/additional-info")
    public String showAdditionalInfoForm(){

        return "signupAddInfo";
    }

    @PostMapping("/submit-additional-info")
    public String submitAdditionalInfo(
            @RequestParam String name,
            @RequestParam String nickname,
            @RequestParam String hp,
            Principal principal)

    {
        AddSignupInfoDto addSignupInfoDto = new AddSignupInfoDto(name, nickname, hp);
        userService.addUserInfo(addSignupInfoDto, principal);

        return "redirect:/home";
    }
}

