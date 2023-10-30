package sjspring.shop.pregAndBirthDeveloper.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.FindUserInfo;
import sjspring.shop.pregAndBirthDeveloper.dto.MailDto;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class SendEmailService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    @Transactional
    public FindUserInfo updateAndSendPwd(FindUserInfo findUserInfoDto){

        //findUserInfoDto에는 현재 유저가 입력한 email만 들어 있음.
        //유저 이메일로 유효한 사용자 인지 확인
        String email = findUserInfoDto.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("NOT FOUND" + email));

        //유효하다면 임시 비번 생성.
        String tempPwd = getTempPassword();
        //임시 비번 암호화/repository에 저장
        user.updatePassword(email, passwordEncoder(tempPwd));

        //dto에 user 정보 담기.
        findUserInfoDto.setPassword(tempPwd);

        //user가 입력한 email로 임시 pw 송부
        emailSender(findUserInfoDto);

        return findUserInfoDto;
    }

    //존재하는 user일 경우 임시 비번 생성
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        StringBuilder tempPwd = new StringBuilder();

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            tempPwd.append(charSet[idx]);
        }
        return tempPwd.toString();
    }

    //메일 송부 메서드
    public void emailSender(FindUserInfo findUserInfo){
        MailDto mailDto = new MailDto(findUserInfo);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(mailDto.getReceiver());
            mimeMessageHelper.setSubject(mailDto.getTitle());
            mimeMessageHelper.setText(mailDto.getMessage(), false);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    //임시로 생성한 비번 encoding
    public String passwordEncoder(String tempPwd){
        return bCryptPasswordEncoder.encode(tempPwd);
    }
}
