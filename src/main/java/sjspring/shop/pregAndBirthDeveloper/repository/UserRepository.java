package sjspring.shop.pregAndBirthDeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sjspring.shop.pregAndBirthDeveloper.domain.User;

import java.util.Optional;

//이메일로 사용자 정보 가져온다.
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
