package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

//사용자 정보를 담고 있는 객체
@Getter
@Setter
@NoArgsConstructor
public class AddUserRequest {
    private String email;
    private String password;
    private String hp;
    private String name;
    private String nickName;
}
