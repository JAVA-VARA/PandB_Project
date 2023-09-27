package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import sjspring.shop.pregAndBirthDeveloper.domain.User;


@Getter
@Setter
@NoArgsConstructor
public class FindUserInfo {
    private String email;
    private String name;
    private String hp;
    private String password;


    public FindUserInfo(String name, String hp) {
        this.name = name;
        this.hp = hp;
    }

    public FindUserInfo(String email) {
        this.email = email;
    }

    public FindUserInfo(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.hp = user.getHp();
        this.password = user.getPassword();
    }

}
