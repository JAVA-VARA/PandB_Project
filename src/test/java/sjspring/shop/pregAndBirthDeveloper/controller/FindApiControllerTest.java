package sjspring.shop.pregAndBirthDeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import sjspring.shop.pregAndBirthDeveloper.service.FindUserService;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("FindApiController 테스트")
public class FindApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    User user;

    @Autowired
    FindUserService findUserService;

    @BeforeEach
    void setUserRepository(){
        userRepository.deleteAll();
        userRepository.save(User.builder()
                .email("valid@gmail.com")
                .name("유효한이름")
                .hp("01042930091")
                .nickName("author")
                .password("Test")
                .build());
    }

    @DisplayName("유효한 이메일 찾기")
    @Test
    public void findValidEmail() throws Exception {

        //GIVEN
        User registeredUser = userService.findByEmail("valid@gmail.com");

        String url = "/showId";
        String validName = "유효한이름";
        String validHp = "01042930091";

        // WHEN
        final ResultActions resultActions= mockMvc
                .perform(MockMvcRequestBuilders
                .get(url)
                .param("name",validName)
                .param("hp",validHp)
                .contentType(MediaType.APPLICATION_JSON));


        // THEN
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(registeredUser.getEmail()));

    }
//    @DisplayName("유효하지 않은 이메일 입력 시 메시지 반환")
//    @Test
//    public void findInvalidEmail() throws Exception {
//
//        // GIVEN
//        String url = "/showId";
//        String invalidName = "유효하지않은이름";
//        String invalidHp = "01012345678";
//        FindUserInfo findUserInfoDto = new FindUserInfo(invalidName, invalidHp);
//
////        assertThatThrownBy(()-> ValidationUtils.find
////
////                );
//
//
////        final ResultActions resultActions= mockMvc
////                .perform(MockMvcRequestBuilders
////                        .get(url)
////                        .param("name",findUserInfoDto.getName())
////                        .param("hp",findUserInfoDto.getHp())
////                        .contentType(MediaType.APPLICATION_JSON));
//
//
//        // THEN
////        resultActions.andExpect(status().isOk())
////                .andExpect(jsonPath("$").value("유효하지 않은 이메일입니다. 다시 입력해주세요."));
//
//        //THEN
//        resultActions.andExpect(status().isBadRequest())
//                .andExpect(result -> {
//                    String errorMessage = result.getResponse().getErrorMessage();
//                    assert(errorMessage != null && errorMessage.contains("일치 하는 회원 정보가 없습니다."));
//
//                });
//    }

}






//    @Test
//    @DisplayName("유효하지 않은 이메일 입력 시 메시지 반환")
//    public void findInvalidEmail() {
//        // GIVEN
//        FindUserService findUserService = Mockito.mock(FindUserService.class);
//        FindApiController findApiController = new FindApiController(findUserService);
//
//        String invalidName = "유효하지않은이름";
//        String invalidHp = "01012345678";
//        FindUserInfo findUserInfoDto = new FindUserInfo(invalidName, invalidHp);
//        String invalidEmail = "invalid@gmail.com";
//
//        Mockito.when(findUserService.findEmail(findUserInfoDto)).thenReturn(invalidEmail);
//
//        Model model = Mockito.mock(Model.class);
//
//        // WHEN
//        String result = findApiController.findId(invalidName, invalidHp, model);
//
//        // THEN
//        assertThat(result).isEqualTo("유효하지 않은 이메일입니다. 다시 입력해주세요.");
//        Mockito.verify(model, Mockito.never()).addAttribute("FindUserInfo", new FindUserInfo());
//    }

