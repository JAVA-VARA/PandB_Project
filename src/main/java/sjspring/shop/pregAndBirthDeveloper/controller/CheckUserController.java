package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.FindUserInfo;
import sjspring.shop.pregAndBirthDeveloper.service.FindUserService;
import sjspring.shop.pregAndBirthDeveloper.service.SendEmailService;

@RequiredArgsConstructor
@RestController
public class CheckUserController {
    private final FindUserService findUserService;
    private final SendEmailService sendEmailService;

    @GetMapping("/showPwd")
    public String findPwd(@RequestParam(value = "email", required = false) String email){

        //Repository에서 받아온 정보를 dto에 담음.
        FindUserInfo findUserInfoDto = new FindUserInfo(email);
        User userInfo = findUserService.findPwd(findUserInfoDto);
        String userEmail = userInfo.getEmail();

        FindUserInfo findUserInfoToUpdate = new FindUserInfo(userInfo);

        if(!userEmail.isEmpty()){
            User updatedUser = sendEmailService.updatePassword(findUserInfoToUpdate);
            return "입력하신 이메일로 임시 비밀번호를 전송하였습니다. ";
//            return "임시비번은" + updatedUser.getPassword() + "입니다.";
        }

        return "입력하신 이메일 주소는 유효하지 않는 아이디 입니다.";



    }

}
