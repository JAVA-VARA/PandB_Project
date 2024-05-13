package sjspring.shop.pregAndBirthDeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.AttachedFileRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardCategoryRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import sjspring.shop.pregAndBirthDeveloper.service.BoardService;
import sjspring.shop.pregAndBirthDeveloper.service.CategoryService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardCategoryRepository boardCategoryRepository;

    @Autowired
    AttachedFileRepository attachedFileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardService boardService;

    User user;

    @Autowired
    CategoryService categoryService;
    @BeforeEach
    void setSecurityContext() {
        userRepository.deleteAll();
        boardRepository.deleteAll();

        if (userRepository.findByEmail("Test@gmail.com").isEmpty()) {
            user = userRepository.save(User.builder()
                    .email("Test@gmail.com")
                    .name("name")
                    .hp("12345678")
                    .nickname("author")
                    .password("Test")
                    .build());
        }

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @AfterEach
    void deleteUserRepository(){
        userRepository.deleteAll();
    }

    @DisplayName("addArticle: 사용자 인증 & 글 추가 성공.")
    @Test
    @Transactional
    public void addArticle() throws Exception {

        //given
        final String url = "/api/articles";
        final String email = "test@email.com";
        final String title = "title";
        final String content = "content";
        final String author = "author";
        final String categoryName = "자유게시판";
        final int views = 0;

        final MockMultipartFile file = new MockMultipartFile(
                "files", "test.txt", MediaType.TEXT_PLAIN_VALUE, "fileContent".getBytes());


        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");


        ResultActions result = mockMvc.perform(multipart(url)
                .file(file)
                .param("category", categoryName)
                .param("title", title)
                .param("content", content)
                .principal(() -> email)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));

        //then
        result.andExpect(status().isCreated());
        List<Board> board = boardRepository.findAll();

        assertThat(board.size()).isEqualTo(1);
        assertThat(board.get(0).getTitle()).isEqualTo(title);
        assertThat(board.get(0).getContent()).isEqualTo(content);
        assertThat(board.get(0).getAuthor()).isEqualTo(author);
        assertThat(board.get(0).getViews()).isEqualTo(views);
        assertThat(board.get(0).getCategory().getCategoryName()).isEqualTo("자유게시판");
        assertThat(board.get(0).getAttachedFileList().get(0).getOriginalFileName()).isEqualTo("test.txt");
        assertThat(board.get(0).getCreatedAt()).isNotNull();
    }




    @DisplayName("findAllArticles: 게시판 글 목록 조회 성공")
    @Test
    @Transactional
    public void findAllArticles() throws Exception {
        //given
        final String url = "/api/articles";
        Board savedBoard = createDefaultArticleAndCategoryMapping();

        //when
        final ResultActions resultActions =
                mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(savedBoard.getTitle()))
                .andExpect(jsonPath("$[0].content").value(savedBoard.getContent()))
                .andExpect(jsonPath("$[0].author").value(savedBoard.getAuthor()))
                .andExpect(jsonPath("$[0].views").value(savedBoard.getViews()))
                .andExpect(jsonPath("$[0].createdAt").isNotEmpty());
    }

    @DisplayName("findArticle: 게시판 글(단일) 조회 성공")
    @Test
    @Transactional
    public void findArticle() throws Exception{
        //given
        final String url = "/api/articles/{board_id}";
        Board savedBoard = createDefaultArticleAndCategoryMapping();

        //when
        final ResultActions resultActions = mockMvc.perform(get(url, savedBoard.getBoardNo()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(savedBoard.getContent()))
                .andExpect(jsonPath("$.title").value(savedBoard.getTitle()))
                .andExpect(jsonPath("$.author").value(savedBoard.getAuthor()))
                .andExpect(jsonPath("$.views").value(savedBoard.getViews()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());

    }

    @DisplayName("deleteArticle: 사용자 인증 & 글 삭제 성공.")
    @Test
    @Transactional
    public void deleteArticle() throws Exception {
        //given
        final String url = "/api/articles/{board_id}";
        Board savedBoard = createDefaultArticleAndCategoryMapping();


        //when
        mockMvc.perform(delete(url,savedBoard.getBoardNo()))
                .andExpect(status().isOk());

        //then
        List<Board> boards = boardRepository.findAll();
        assertThat(boards).isEmpty();
    }

    @DisplayName("updateArticle: 사용자 인증 & 글 수정 성공.")
    @Test
    @Transactional
    public void updateArticle() throws Exception {
        //given
        final String url = "/api/articles/{board_id}";
        Board savedBoard = createDefaultArticleAndCategoryMapping();


        final String newTitle = "new title";
        final String newContent = "new content";
        final String newCategory = "자유게시판";
        final MockMultipartFile file = new MockMultipartFile(
                "files", "test.txt", MediaType.TEXT_PLAIN_VALUE, "fileContent".getBytes());
        final LocalDateTime updatedAt = LocalDateTime.now();
        final String email = "test@email.com";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent, newCategory, updatedAt);

        ResultActions result2 = mockMvc.perform(put(url, savedBoard.getBoardNo())
                .param("category", newCategory)
                .param("title", newTitle)
                .param("content", newContent)
                .principal(() -> email)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));


        //then
        result2.andExpect(status().isOk());

        Optional<Board> board = boardRepository.findById(savedBoard.getBoardNo());

        assertThat(board.get().getTitle()).isEqualTo(newTitle);
        assertThat(board.get().getContent()).isEqualTo(newContent);
        assertThat(board.get().getCategory().getCategoryName()).isEqualTo(newCategory);
        assertThat(board.get().getUpdatedAt()).isNotNull();
    }

    private Board createDefaultArticleAndCategoryMapping(){
        boardRepository.deleteAll();
        boardCategoryRepository.deleteAll();

        BoardCategory boardCategory =  createDefalutCategory();

        Board board = boardRepository.save(Board.builder()
                .title("title")
                .author("author")
                .content("content")
                .email("Test@gmail.com")
                .category(boardCategory)
                .views(100)
                .build());

        boardCategory.mappingBoard(board);

        return board;
    }

    //카테고리 저장 여부 확인 메서드
    private BoardCategory createDefalutCategory() {
        String categoryName = "자유게시판";
        return categoryService.save(categoryName);
    }
}