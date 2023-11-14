package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private final String sender = "sj.youns1027@gmail.com";
    private final  String title = "쭈쭈르 홈페이지 임시비밀번호 안내";
    private  String message;
    private  String receiver;

    public MailDto(FindUserInfo findUserInfo){
        this.receiver = findUserInfo.getEmail();
        this.message =  "안녕하세요 쭈주르입니다." + findUserInfo.getName() + " 회원님의 임시 비밀번호는 " + findUserInfo.getPassword() + " 입니다.";
    }
}
