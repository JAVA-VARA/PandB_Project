package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

//사용자 정보를 담고 있는 객체
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {
    @NotNull(message="이메일을 입력해주세요.")
    private String email;

    @NotNull(message="이름을 입력해주세요.")
    private String name;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotNull(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2 ~ 10자 사이로 입력해주세요")
    private String nickName;

    private String hp;

    private Date babyDue;
}
