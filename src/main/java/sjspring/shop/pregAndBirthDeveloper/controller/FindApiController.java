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

        if(!findUser.isEmpty()){
            model.addAttribute("FindUserInfo", findUserInfoToView);
            return findUser;
        }

        return "유효하지 않은 이메일입니다. 다시 입력해주세요.";
    }

//    @GetMapping("/showPwd")
//    public String findPwd(@RequestParam(value = "email", required = false) String email, Model model){
//
//        //Repository에서 받아온 정보를 dto에 담음.
//        FindUserInfo findUserInfoDto = new FindUserInfo(email);
//        User findUserPwd = findUserService.findPwd(findUserInfoDto);
//        String userEmail = findUserPwd.getEmail();
//        String userPwdToken = findUserPwd.getPassword();
//
//        //decode하기 위한 service로 보내기 위해 가져온 정보를 dto에 담는다.
//        FindUserInfo findUserInfoDtoToDecode = new FindUserInfo(findUserPwd);
//
//        //1. 가져온 비번 디코드
//
//        //2. 디코드한 비번을 사용자의 이메일로 전송
//
//        //3. 전송완료 문구를 페이지에 표시.
//
//
//
//        return userEmail + " : " + userPwdToken;
//    }
}

//if (!name.isEmpty() && !hp.isEmpty())


//
//    @GetMapping("/findUserId")
//    public ResponseEntity<String> findId(@RequestBody FindUserInfo findUserInfoDto) {
//        String findUser = findUserService.findId(findUserInfoDto);
//
//        public String showId(Model model){
//            model.addAttribute("email", findUser);
//        }
//
//        return
//    }
//
//
//}

//    @PostMapping("/findId")
//    public User findId(FindUserInfo findUserInfoDto){
//
//        return findUserService.findId(findUserInfoDto);
//    }


//    @PostMapping("/findId")
//    public String findId(FindUserInfo findUserInfoDto){
//        findUserService.findId(findUserInfoDto);
//
//        return "/showId";
//    }

//이름. 핸드폰 번호와 일치하는 아이디가 있는 경우 이메일 주소 팝업
//    @GetMapping("/showId")
//    public String showId(){
//        return "/showId";
//    }


//받아온 dto의 정보로 userId 반환
//    @GetMapping("/showId")
//    public ResponseEntity<String> findId(@ModelAttribute FindUserInfo findUserInfoDto) {
//        String findUser = findUserService.findId(findUserInfoDto);
//
//        return ResponseEntity.ok()
//                .body(findUser);

//    }

//    @GetMapping("/showId")
//    public ResponseEntity<String> findId(@RequestParam("name") FindUserInfo findUserInfoDto) {
//
//        String findUser = findUserService.findId(findUserInfoDto);
//
//        return ResponseEntity.ok()
//                .body(findUser);
//
//    }

