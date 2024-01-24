package sjspring.shop.pregAndBirthDeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.AddSignupInfoDto;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

import java.security.Principal;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AdditionalInfoControllerTest")
class AdditionalInfoControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUserRepository(){
        userRepository.deleteAll();
        User user =  userRepository.save(User.builder()
                .email("valid@gmail.com")
                .name("test")
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }
    @DisplayName("Oauth2 회원가입")
    @Test
    public void submitAdditionalInfo() throws Exception {
        //GIVEN
        final String url = "/submit-additional-info";
        final String signupName = "이름";
        final String signupNickname = "유효한이름";
        final String signupHp = "01042930091";
        final Date babyDue = new Date();
        AddSignupInfoDto addSignupInfoDto = new AddSignupInfoDto(signupName, signupNickname, signupHp, babyDue);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("valid@gmail.com");

        final String requestBody = objectMapper.writeValueAsString(addSignupInfoDto);

        //WHEN
        ResultActions result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
                .principal(principal));

        //THEN
        result
                .andExpect(status().isOk());
    }

}