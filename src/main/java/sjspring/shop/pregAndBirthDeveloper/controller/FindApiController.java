package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sjspring.shop.pregAndBirthDeveloper.dto.FindUserInfo;
import sjspring.shop.pregAndBirthDeveloper.service.FindUserService;

@RequiredArgsConstructor
@Controller //http response Body에 객체 데이터를 JSON형식으로 반환하는 컨트롤러
public class FindApiController {
    private final FindUserService findUserService;

    @GetMapping("/showId")
    public String findId(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "hp", required = false) String hp,
            Model model) {

        FindUserInfo findUserInfoDto = new FindUserInfo(name, hp);

        try {
            String findUserEmail = findUserService.findEmail(findUserInfoDto);
            findUserInfoDto.setMessage("귀하의 아이디는" + findUserEmail + " 입니다.");
            model.addAttribute("FindUserInfo", findUserInfoDto);

        } catch (Exception e){
            findUserInfoDto.setMessage("일치하는 이메일이 없습니다.");
            model.addAttribute("FindUserInfo", findUserInfoDto);
        }

        return "showId";
    }
}