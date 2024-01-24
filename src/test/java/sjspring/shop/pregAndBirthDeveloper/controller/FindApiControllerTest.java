package sjspring.shop.pregAndBirthDeveloper.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import sjspring.shop.pregAndBirthDeveloper.service.FindUserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("FindApiControllerTest")
class FindApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindUserService findUserService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUserRepository(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유효한 id를 찾아올 수 있다.")
    void findId_Success() throws Exception {
        // Given
        User testUser =  userRepository.save(User.builder()
                .email("BangGu@example.com")
                .name("BBONG")
                .nickname("nick")
                .hp("01012345678")
                .password("1234")
                .build());

        final String url = "/showId";
        final String name = "BBONG";
        final String hp = "01012345678";
        final String userEmail = "BangGu@example.com";

        // When
        ResultActions result = mockMvc.perform(
                get(url)
                        .param("name", name)
                        .param("hp", hp));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(view().name("showId"));
    }

    @Test
    @DisplayName("찾는 id가 없다.")
    void findId_Failed() throws Exception {
        // Given
        User testUser =  userRepository.save(User.builder()
                .email("BangGu@example.com")
                .name("BBONG")
                .nickname("nick")
                .hp("01012345678")
                .password("1234")
                .build());

        final String url = "/showId";
        final String name = "WRONG";
        final String hp = "WRONG";
        final String userEmail = "WRONG@example.com";

        // When
        ResultActions result = mockMvc.perform(
                get(url)
                        .param("name", name)
                        .param("hp", hp));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(view().name("showId"));

    }
}