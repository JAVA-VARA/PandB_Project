//package sjspring.shop.pregAndBirthDeveloper.util;
//
///*
//1. file upload 여부
//2. resizing 여부와, 얼마나줄어드는지 확인
//3. 용량별 test
//* */
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import sjspring.shop.pregAndBirthDeveloper.domain.User;
//import sjspring.shop.pregAndBirthDeveloper.repository.AttachedFileRepository;
//import sjspring.shop.pregAndBirthDeveloper.repository.BoardRepository;
//import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
//
//import java.io.FileInputStream;
//import java.security.Principal;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class LocalUploadUtilTest {
//
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Autowired
//    protected ObjectMapper objectMapper;
//
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Autowired
//    AttachedFileRepository attachedFileRepository;
//
//
//    @Autowired
//    ResourceLoader loader;
//
//    User user;
//    @BeforeEach
//    void setUpUserInfo(){
////        attachedFileRepository.deleteAll();
//        userRepository.deleteAll();
//        boardRepository.deleteAll();
//
//        user = userRepository.save(User.builder()
//                    .email("Test@gmail.com")
//                    .name("name")
//                    .hp("12345678")
//                    .nickname("author")
//                    .password("Test")
//                    .build());
//
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(
//                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
//        }
//
//    @Test
//    @DisplayName("fileUploadToLocalDir 메서드 테스트")
//    void fileUploadToLocalDir() throws Exception {
//
//        //GIVEN
//        Principal principal = Mockito.mock(Principal.class);
//        Mockito.when(principal.getName()).thenReturn("username");
//        //BoardApiController 와 동일하게 파일 업로드 진행
//        final String url = "/api/articles";
//        final String email = "test@gmail.com";
//        final String title = "title";
//        final String content = "content";
//        final String categoryName = "자유게시판";
//        final String filePath = "C:\\Users\\sjyou\\IdeaProjects\\PandBproject\\PandB_Project\\src\\main\\resources\\testingImages\\";
//
////        MockMultipartFile webpImageFile = new MockMultipartFile(
////                "files",
////                "webTest.webp",
////                "image/webp",
////                new FileInputStream(filePath + "webTest.webp"));
////
////        MockMultipartFile jpgImageFile = new MockMultipartFile(
////                "files",
////                "JpgTest.jpg",
////                "image/jpeg",
////                new FileInputStream(filePath + "JpgTest.jpg"));
////
////        MockMultipartFile pngImageFile = new MockMultipartFile(
////                "files",
////                "PngTest.png",
////                "image/png",
////                new FileInputStream(filePath + "PngTest.png"));
////
////        MockMultipartFile hiecImageFile = new MockMultipartFile(
////                "files",
////                "HeicTest.heic",
////                "application/octet-stream",
////                new FileInputStream(filePath + "HeicTest.heic"));
//
//        MockMultipartFile gifImageFile = new MockMultipartFile(
//                "files",
//                "GifTest.gif",
//                "image/gif",
//                new FileInputStream(filePath + "GifTest.gif"));
//
////        MockMultipartFile avifImageFile = new MockMultipartFile(
////                "files",
////                "AvifTest.avif",
////                "image/avif",
////                new FileInputStream(filePath + "AvifTest.avif"));
//
//
//
//
//
//        //WHEN
////        ResultActions WEBPresultAction = mockMvc.perform(multipart(url)
////                .file(webpImageFile)
////                .param("category", categoryName)
////                .param("title", title)
////                .param("content", content)
////                .principal(() -> email)
////                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
////        ResultActions JPGresultAction = mockMvc.perform(multipart(url)
////                .file(jpgImageFile)
////                .param("category", categoryName)
////                .param("title", title)
////                .param("content", content)
////                .principal(() -> email)
////                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
////        ResultActions PNGresultAction = mockMvc.perform(multipart(url)
////                .file(pngImageFile)
////                .param("category", categoryName)
////                .param("title", title)
////                .param("content", content)
////                .principal(() -> email)
////                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
////        ResultActions HEICresultAction = mockMvc.perform(multipart(url)
////                .file(hiecImageFile)
////                .param("category", categoryName)
////                .param("title", title)
////                .param("content", content)
////                .principal(() -> email)
////                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
//        ResultActions GIFresultAction = mockMvc.perform(multipart(url)
//                .file(gifImageFile)
//                .param("category", categoryName)
//                .param("title", title)
//                .param("content", content)
//                .principal(() -> email)
//                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
////        ResultActions AVIFresultAction = mockMvc.perform(multipart(url)
////                .file(avifImageFile)
////                .param("category", categoryName)
////                .param("title", title)
////                .param("content", content)
////                .principal(() -> email)
////                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
//
//
//
//        //THEN
////        WEBPresultAction.andExpect(status().isCreated());
////        JPGresultAction.andExpect(status().isCreated());
////        PNGresultAction.andExpect(status().isCreated());
//        GIFresultAction.andExpect(status().isCreated());
////        AVIFresultAction.andExpect(status().isCreated());
////        HEICresultAction.andExpect(status().isCreated());
//
////        List<AttachedFile> fileS = attachedFileRepository.findAll();
////
////        System.out.println();
////        assertThat(fileS.size()).isEqualTo();
////        assertThat(fileS.get(0).getOriginalFileName()).isEqualTo("testingimage.JPG");
////        assertThat(fileS.get(0).getOriginalFileName()).isEqualTo("testingimage.JPG");
////        assertThat(fileS.get(0).getOriginalFileName()).isEqualTo("testingimage.JPG");
////
////
////        Runtime runtime = Runtime.getRuntime();
////        long totalMemory = runtime.totalMemory();
////        long freeMemory = runtime.freeMemory();
////        long usedMemory = totalMemory - runtime.freeMemory();
////
////        System.out.println("Max Memory:" + runtime.maxMemory());
////        System.out.println("Total Memory:" + totalMemory);
////        System.out.println("Free Memory:" + freeMemory);
////        System.out.println("Used Memory:" +usedMemory);
//    }
//    @Test
//    @DisplayName("resizer 테스트: 리사이징, 업로드한 파일의 용량 감소")
//    void resizer() {
//
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//    }
//}
//
////
////        String filename = "testingimage.JPG";
////        Resource resource = loader.getResource("classpath:/static/testingImages/" + filename);
////        MockMultipartFile imageFile = new MockMultipartFile("file", filename, null, resource.getInputStream());
//
//
//
////        final MockMultipartFile file = new MockMultipartFile(
////                "files", "test.txt", MediaType.TEXT_PLAIN_VALUE, "fileContent".getBytes());
//
//
//
////        String video98MB  = "classpath:images/98mb.MOV";
////        String video100MB = "classpath:images/100mb.MOV";
////        String video115MB = "classpath:images/115mb.MOV";
////        MockMultipartFile videoFile1 = createMockMultipartFile(video98MB, "video/quicktime");
////        MockMultipartFile videoFile2 = createMockMultipartFile(video100MB, "video/quicktime");
////        MockMultipartFile videoFile3 = createMockMultipartFile(video115MB, "video/quicktime");
//
////        String filename = "testingimage.JPG";
////        Resource resource = loader.getResource("classpath:/static/testingImages/" + filename);
////        MockMultipartFile imageFile = createMockMultipartFile(resource.getFile().getPath(), null);
//
////        String imagePath  = "/src/main/resources/testingImages/" + filename;
////        MockMultipartFile imageFile = createMockMultipartFile(imagePath, "image/jpeg");
//
////        String filename = "testingimage.JPG";
////        Resource resource = loader.getResource("src/main/resources/testingImages/" + filename);
////        MockMultipartFile imageFile = new MockMultipartFile("file", filename, "image/jpeg", resource.getInputStream());