package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {
    private String nickname;
    private String hp;
    private Date babyDue;
}
