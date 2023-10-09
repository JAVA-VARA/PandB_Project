package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.FindUserInfo;
import sjspring.shop.pregAndBirthDeveloper.service.FindUserService;

@RequiredArgsConstructor
@RestController //http response Body에 객체 데이터를 JSON형식으로 반환하는 컨트롤러
public class FindApiController {
    private final FindUserService findUserService;

    @GetMapping("/showId")
    public String findId(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "hp", required = false) String hp,
            Model model) {

        FindUserInfo findUserInfoDto = new FindUserInfo(name, hp);
        String findUser = findUserService.findId(findUserInfoDto);

        FindUserInfo findUserInfoToView = new FindUserInfo();
        findUserInfoToView.setEmail(findUser);

        if (!findUser.isEmpty()) {
            model.addAttribute("FindUserInfo", findUserInfoToView);
            return findUser;
        }

        return "유효하지 않은 이메일입니다. 다시 입력해주세요.";
    }
}