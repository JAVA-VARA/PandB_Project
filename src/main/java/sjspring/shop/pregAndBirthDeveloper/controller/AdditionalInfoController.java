package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PutMapping("/submit-additional-info")
    public ResponseEntity<Void> submitAdditionalInfo(
            @RequestBody AddSignupInfoDto addSignupInfoDto,

            Principal principal)

    {
//        AddSignupInfoDto = new AddSignupInfoDto(name, nickname, hp, babyDue);
        userService.addUserInfo(addSignupInfoDto, principal);
//
        return ResponseEntity.ok()
                .build();
    }
}

