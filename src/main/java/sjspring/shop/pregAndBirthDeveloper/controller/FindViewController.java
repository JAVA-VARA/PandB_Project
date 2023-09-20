package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sjspring.shop.pregAndBirthDeveloper.dto.FindUserInfo;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

@Controller
@NoArgsConstructor
public class FindViewController {

    //아이디 찾기 페이지
//    @GetMapping("/findId")
//    public String findId() {
//        return "findId";
//    }

    //findId로 비어있는 dto 전달.
//    @GetMapping("/findId")
//    public String findUser(Model model, FindUserInfo findUserInfoDto){
//        model.addAttribute("findUserInfoDto" , findUserInfoDto);
//
//        return "findId";
//    }

    @GetMapping("/findId")
    public String findUser(){
        return "findId";
    }



    @GetMapping("/findPwd")
    public String findPwd() {
        return "findPwd";
    }
}
