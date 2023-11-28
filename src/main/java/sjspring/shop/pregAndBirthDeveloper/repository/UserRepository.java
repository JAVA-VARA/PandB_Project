package sjspring.shop.pregAndBirthDeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sjspring.shop.pregAndBirthDeveloper.domain.User;

import java.util.Optional;

//사용자 정보 가져온다.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Optional<Integer> findIdByEmail(@Param("email") String email);

    @Query("SELECT u.email FROM User u WHERE u.name = :name AND u.hp = :hp")
    Optional<String> findEmailByNameAndHp(@Param("name") String name, @Param("hp") String hp);
}