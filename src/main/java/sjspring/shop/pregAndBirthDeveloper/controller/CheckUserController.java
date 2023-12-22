package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sjspring.shop.pregAndBirthDeveloper.dto.FindUserInfo;
import sjspring.shop.pregAndBirthDeveloper.service.SendEmailService;

@RequiredArgsConstructor
@Controller
public class CheckUserController {
    private final SendEmailService sendEmailService;

    @GetMapping("/showPwd")
    public String findPwd(@RequestParam(value = "email") FindUserInfo findUserInfoDto, Model model) {
        model.addAttribute("isSuccess", sendEmailService.updateAndSendPwd(findUserInfoDto));

        return "showPwd";
    }
}

/*
* showPwd로 보여지는 것은 메일이 보내졌냐/아니냐만 판단하면 됨.
* 굳이 user의 정보를 dto에 넣고 다니면서 확인할 필요 없음
* service layer에서 메일 보내는 로직 수행
* 성공하면 isSuccess 는 true
* 실패하면 isSuccess 는 false
* */

