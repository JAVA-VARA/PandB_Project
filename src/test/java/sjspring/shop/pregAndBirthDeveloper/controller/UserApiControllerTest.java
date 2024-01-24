package sjspring.shop.pregAndBirthDeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.AddUserRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateUserRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UserApiControllerTest 테스트")
class UserApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

//    private static UserRepository userRepository;


    @Autowired
    UserService userService;

    @Test
    @DisplayName("일반 회원가입 - 정상")
    @Order(1)
    void testSignupWithValidRequest() throws Exception {

        userRepository.deleteAll();

        //given
        final String url = "/users";
        final String email = "milion1234@naver.com";
        final String signupName = "이름2";
        final String signupNickname = "닉네임이다";
        final String signupHp = "01042930091111";
        final String pw = "1234";
        final Date babyDue = new Date();
        AddUserRequest validRequest  = new AddUserRequest(email, signupName, pw, signupNickname, signupHp,babyDue);
        final String requestBody = objectMapper.writeValueAsString(validRequest);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        //then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("일반 회원가입 - 오류")
    @Order(2)
    void testSignupWithInvalidRequest() throws Exception {
        // given
        final String url = "/users";
        final String email = "milion1234@naver.com";
        final String signupName = "이름";
        final String signupNickname = "닉네임이다";
        final String signupHp = "01042930091";
        final String pw = "1234";
        final Date babyDue = new Date();
        AddUserRequest validRequest  = new AddUserRequest(email, signupName, pw, signupNickname, signupHp,babyDue);
        final String requestBody = objectMapper.writeValueAsString(validRequest);

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        // then
        result
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회정 정보 수정")
    void updateInfo() throws Exception {
        userRepository.deleteAll();
        //given
        final String url = "/update/users";
        final String email = "sj@gmail.com";
        final String hp = "01042930091";
        final String name = "풍풍풍";
        final String nickname = "풋팡퐁커리";
        final String password = "test";
        final Date babyDue = new Date();

        User testUser = userRepository.save(User.builder()
                .email(email)
                .hp(hp)
                .name(name)
                .password(password)
                .nickname(nickname)
                .babyDue(babyDue)
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(testUser, testUser.getPassword(), testUser.getAuthorities()));

        final String updatedHp = "NEW";
        final String updatedNickname = "NEW";
        final String updatedPassword = "NEW";
        final Date updatedBabyDue = new Date();

        UpdateUserRequest updateRequest  = new UpdateUserRequest(updatedNickname, updatedHp, updatedPassword, updatedBabyDue);
        final String requestBody = objectMapper.writeValueAsString(updateRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
                put(url).principal(() -> email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        resultActions
                .andExpect(status().isOk());

        User updatedUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        assertEquals(updatedHp, updatedUser.getHp());
        assertEquals(updatedNickname, updatedUser.getNickname());
        assertEquals(updatedBabyDue, updatedUser.getBabyDue());

    }


    @Test
    @DisplayName("회정 정보 삭제")
    void deleteUser() throws Exception {
        userRepository.deleteAll();
        //given
        final String url = "/delete/users";
        final String email = "sj@gmail.com";
        final String hp = "01042930091";
        final String name = "풍풍풍";
        final String nickname = "풋팡퐁커리";
        final String password = "test";
        final Date babyDue = new Date();

        User testUser = userRepository.save(User.builder()
                .email(email)
                .hp(hp)
                .name(name)
                .password(password)
                .nickname(nickname)
                .babyDue(babyDue)
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(testUser, testUser.getPassword(), testUser.getAuthorities()));

        // when
        ResultActions result = mockMvc.perform(
                delete(url)
                        .principal(() -> email));

        //then
        result.andExpect(status().isOk());
    }

}