package sjspring.shop.pregAndBirthDeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.FindUserInfo;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FindUserService {
    private final UserRepository userRepository;

    public String findEmail(FindUserInfo findUserInfoDto){
        String name = findUserInfoDto.getName();
        String hp = findUserInfoDto.getHp();

        return userRepository.findEmailByNameAndHp(name, hp)
                .orElseThrow(() -> new IllegalArgumentException("일치 하는 회원 정보가 없습니다."));
    }

    public User findPwd(FindUserInfo findUserInfoDto){
        String email = findUserInfoDto.getEmail();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(email + "와 일치하는 회원정보가 없습니다."));
    }

}
