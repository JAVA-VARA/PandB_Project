package sjspring.shop.pregAndBirthDeveloper.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.FindUserInfo;
import sjspring.shop.pregAndBirthDeveloper.dto.MailDto;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import sjspring.shop.pregAndBirthDeveloper.util.PasswordMakerUtil;

@RequiredArgsConstructor
@Service
public class SendEmailService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;


    @Transactional
    public Boolean updateAndSendPwd(FindUserInfo findUserInfoDto)  {
        try{

            String email = findUserInfoDto.getEmail();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("NOT FOUND: " + email));

            String tempPwd = PasswordMakerUtil.makeTempPassword();
            user.updatePassword(email, passwordEncoder(tempPwd));

            findUserInfoDto.setPassword(tempPwd);
            emailSender(findUserInfoDto);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public void emailSender(FindUserInfo findUserInfo) throws MessagingException {
        MailDto mailDto = new MailDto(findUserInfo);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(mailDto.getReceiver());
        mimeMessageHelper.setSubject(mailDto.getTitle());
        mimeMessageHelper.setText(mailDto.getMessage(), false);
        javaMailSender.send(mimeMessage);

    }
    public String passwordEncoder(String tempPwd){
        return bCryptPasswordEncoder.encode(tempPwd);
    }
}
