package sjspring.shop.pregAndBirthDeveloper.service;


import lombok.RequiredArgsConstructor;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.AddUserRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    //회원 가입 시 추가 입력 기능 TBD
    public Long save(String name, String hp) {
        return userRepository.save(User.builder()
                .name(name)
                .hp(hp)
                .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
