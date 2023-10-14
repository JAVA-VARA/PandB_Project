package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@NoArgsConstructor
public class FindViewController {

    @GetMapping("/findId")
    public String findUser(){
        return "findId";
    }

    @GetMapping("/findPwd")
    public String findPwd() {
        return "findPwd";
    }
}
