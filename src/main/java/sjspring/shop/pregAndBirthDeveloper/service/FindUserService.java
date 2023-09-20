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

    public String findId(FindUserInfo findUserInfoDto){
        String name = findUserInfoDto.getName();
        String hp = findUserInfoDto.getHp();

        System.out.println(name + " " + hp);

        return userRepository.findEmailByNameAndHp(name, hp)
                .orElseThrow(() -> new IllegalArgumentException("일치 하는" + name + hp + "정보가 없습니다."));
    }
}
