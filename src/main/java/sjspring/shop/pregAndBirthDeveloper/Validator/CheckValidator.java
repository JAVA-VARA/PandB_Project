package sjspring.shop.pregAndBirthDeveloper.Validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import sjspring.shop.pregAndBirthDeveloper.dto.AddUserRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class CheckValidator extends AbstractValidator<AddUserRequest>{

    private final UserRepository userRepository;

    @Override
    protected void doValidate(@RequestBody AddUserRequest dto, Errors errors) {
        if(userRepository.existsByEmail(dto.getEmail())){
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용 중인 이메일 입니다.");
        }
        if(userRepository.existsByHp(dto.getHp())){
            errors.rejectValue("hp", "휴대폰 번호 중복 오류", "이미 사용 중인 번호 입니다.");
        }

        if(userRepository.existsByNickname(dto.getNickname())){
            errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용 중인 닉네임 입니다.");
        }

    }
}
