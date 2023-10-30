package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.FindUserInfo;
import sjspring.shop.pregAndBirthDeveloper.service.FindUserService;
import sjspring.shop.pregAndBirthDeveloper.service.SendEmailService;

@RequiredArgsConstructor
@Controller
public class CheckUserController {
    private final SendEmailService sendEmailService;

    @GetMapping("/showPwd")
    public String findPwd(@RequestParam(value = "email", required = false) String email, Model model) {

        FindUserInfo findUserInfoDto = new FindUserInfo(email);
        FindUserInfo responseDto  = sendEmailService.updateAndSendPwd(findUserInfoDto);
        model.addAttribute("userInfo", responseDto);
        return "/showPwd";
    }
}

