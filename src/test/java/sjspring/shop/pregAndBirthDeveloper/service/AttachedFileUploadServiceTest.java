package sjspring.shop.pregAndBirthDeveloper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.repository.AttachedFileRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AttachedFileUploadServiceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    AttachedFileRepository attachedFileRepository;

    @Autowired
    ResourceLoader loader;

    User user;
    @BeforeEach
    void setUpUserInfo(){
        userRepository.deleteAll();
        boardRepository.deleteAll();

        user = userRepository.save(User.builder()
                .email("Test@gmail.com")
                .name("name")
                .hp("12345678")
                .nickname("author")
                .password("Test")
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @Test
    @DisplayName("fileUploadToLocalDir 메서드 테스트")
    void fileUploadToLocalDir() throws Exception {

        //GIVEN
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        //BoardApiController 와 동일하게 파일 업로드 진행
        final String url = "/api/articles";
        final String email = "test@gmail.com";
        final String title = "title";
        final String content = "content";
        final String categoryName = "자유게시판";
        final String filePath = "C:\\Users\\sjyou\\IdeaProjects\\PandBproject\\PandB_Project\\src\\main\\resources\\testingImages\\";

        // 테스트할 이미지 파일들 설정
        MockMultipartFile[] imageFiles = {
                createMockMultipartFile("webTest.webp", "image/webp", filePath),
                createMockMultipartFile("JpgTest.jpg", "image/jpeg", filePath),
                createMockMultipartFile("PngTest.png", "image/png", filePath),
                createMockMultipartFile("GifTest.gif", "image/gif", filePath),
                createMockMultipartFile("HeicTest.heic", "application/octet-stream", filePath),
                createMockMultipartFile("AvifTest.avif", "image/avif", filePath)
        };

        //WHEN & THEN
        for (MockMultipartFile imageFile : imageFiles) {
            performMultipartRequest(url, categoryName, title, content, email, imageFile)
                    .andExpect(status().isCreated());
        }

        // 메모리 상태 출력 (테스트 용도)
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - runtime.freeMemory();

        System.out.println("Max Memory:" + runtime.maxMemory());
        System.out.println("Total Memory:" + totalMemory);
        System.out.println("Free Memory:" + freeMemory);
        System.out.println("Used Memory:" + usedMemory);
    }

    private MockMultipartFile createMockMultipartFile(String fileName, String contentType, String filePath) throws IOException {
        return new MockMultipartFile(
                "files",
                fileName,
                contentType,
                new FileInputStream(filePath + fileName)
        );
    }

    // Multipart 요청 수행 메서드
    private ResultActions performMultipartRequest(String url, String categoryName, String title, String content, String email, MockMultipartFile file) throws Exception {
        return mockMvc.perform(multipart(url)
                .file(file)
                .param("category", categoryName)
                .param("title", title)
                .param("content", content)
                .principal(() -> email)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
    }
}