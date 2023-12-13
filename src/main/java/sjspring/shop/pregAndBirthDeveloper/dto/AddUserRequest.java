package sjspring.shop.pregAndBirthDeveloper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//사용자 정보를 담고 있는 객체
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {
    @NotBlank(message="이메일을 입력해주세요.")
    private String email;

    @NotBlank(message="이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2 ~ 10자 사이로 입력해주세요")
    private String nickName;

    private String hp;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String babyDue;


}
