package sjspring.shop.pregAndBirthDeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import sjspring.shop.pregAndBirthDeveloper.service.SendEmailService;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CheckUserControllerTest")
class CheckUserControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SendEmailService sendEmailService;

    User user;

    @Test
    void findPwd() throws Exception {
        userRepository.deleteAll();
        //GIVEN
        final String url = "/showPwd";
        final String email = "milion123@naver.com";
        final String signupName = "이름";
        final String signupNickname = "닉네임";
        final String signupHp = "01042930091";
        final String pw = "1234";
        final Date babyDue = new Date();

        user = userRepository.save(User.builder()
                .email(email)
                .hp(signupHp)
                .name(signupName)
                .password(pw)
                .nickname(signupNickname)
                .babyDue(babyDue)
                .build());

        //WHEN
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("email", email));

        //THEN
        resultActions.andExpect(status().isOk());

    }
}