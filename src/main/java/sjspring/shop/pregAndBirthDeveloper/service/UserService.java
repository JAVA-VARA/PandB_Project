package sjspring.shop.pregAndBirthDeveloper.service;


import lombok.RequiredArgsConstructor;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.AddSignupInfoDto;
import sjspring.shop.pregAndBirthDeveloper.dto.AddUserRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateUserRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .nickName(dto.getNickName())
                .name(dto.getName())
                .hp(dto.getHp())
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    //babyDue 추가 필요함!!!
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

        user.update(nickName, hp, babyDue);

        userRepository.save(user);
    }

    public void delete(Principal principal){
        String email = principal.getName();
        Long id = userRepository.findByEmail(email).get().getId();
        userRepository.deleteById(id);
    }

}
