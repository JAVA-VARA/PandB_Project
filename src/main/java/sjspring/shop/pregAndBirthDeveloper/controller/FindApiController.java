package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sjspring.shop.pregAndBirthDeveloper.dto.FindUserInfo;
import sjspring.shop.pregAndBirthDeveloper.service.FindUserService;

@RequiredArgsConstructor
@RestController //http response Body에 객체 데이터를 JSON형식으로 반환하는 컨트롤러
public class FindApiController {
    private final FindUserService findUserService;

    @GetMapping("/showId")
    public ResponseEntity<String> findId(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "hp", required = false) String hp,
            Model model) {

        FindUserInfo findUserInfoDto = new FindUserInfo(name, hp);

        String findUser = findUserService.findEmail(findUserInfoDto);

        FindUserInfo findUserInfoToView = new FindUserInfo();
        findUserInfoToView.setEmail(findUser);

        if (!findUser.isEmpty()) {
            model.addAttribute("FindUserInfo", findUserInfoToView);

            return ResponseEntity.ok()
                    .body(findUser);
        }

        return ResponseEntity.ok()
                .body("일치 하는 회원 정보가 없습니다.");
    }
}