package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class CreateAccessTokenRequest {
    private String refreshToken;
}
