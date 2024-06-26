package sjspring.shop.pregAndBirthDeveloper.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.AddSignupInfoDto;
import sjspring.shop.pregAndBirthDeveloper.dto.AddUserRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateUserRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public Long save(AddUserRequest dto){

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .name(dto.getName())
                .hp(dto.getHp())
                .babyDue(dto.getBabyDue())
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    public void addUserInfo(AddSignupInfoDto addSignupInfoDto, Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        String name = addSignupInfoDto.getName();
        String nickName = addSignupInfoDto.getNickname();
        String hp = addSignupInfoDto.getHp();
        Date babyDue = addSignupInfoDto.getBabyDue();

        user.update(name, nickName, hp, babyDue);

        userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public void update(UpdateUserRequest request, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        String nickName = request.getNickname();
        String hp = request.getHp();
        Date babyDue = request.getBabyDue();

        if(request.getPassword() != null) {
            user.update(nickName, hp, babyDue, encoder.encode(request.getPassword()));
        }
        else {
            user.update(nickName, hp, babyDue);
        }

        userRepository.save(user);
    }

    public void delete(Principal principal){
        String email = principal.getName();
        Long id = userRepository.findByEmail(email).get().getId();
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;

    }

}
