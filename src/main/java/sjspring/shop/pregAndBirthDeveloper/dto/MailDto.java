package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String sender;
    private String receiver;
    private String title;
    private String message;
}
