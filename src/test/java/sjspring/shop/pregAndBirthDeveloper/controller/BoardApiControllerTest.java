package sjspring.shop.pregAndBirthDeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.AddArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardCategoryRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import sjspring.shop.pregAndBirthDeveloper.service.CategoryService;

import static org.assertj.core.api.Assertions.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    UserRepository userRepository;

    User user;

    @Autowired
    CategoryService categoryService;
    @BeforeEach
    void setSecurityContext() {
        userRepository.deleteAll();

        if (userRepository.findByEmail("Test@gmail.com").isEmpty()) {
            user = userRepository.save(User.builder()
                    .email("Test@gmail.com")
                    .nickName("author")
                    .password("Test")
                    .build());
        }

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @DisplayName("addArticle: 사용자 인증 & 글 추가 성공.")
    @Test
    @Transactional
    public void addArticle() throws Exception {

        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final String author = "author";
        final String categoryName = "자유게시판";
        final LocalDateTime createdAt = LocalDateTime.now();
        final int views = 100;

        //카테고리 저장 여부 확인
        final BoardCategory boardCategory = categoryService.save(categoryName);

        final AddArticleRequest userRequest = new AddArticleRequest(title,content,author,categoryName,boardCategory, views);
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        //
        userRequest.toEntity(principal.getName());
        userRequest.setBoardCategory(boardCategory);

        //
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when: 설정한 내용을 바탕으로 요청 전송(post 요청 시뮬레이션)
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());
        List<Board> board = boardRepository.findAll();

        assertThat(board.size()).isEqualTo(1);
        assertThat(board.get(0).getTitle()).isEqualTo(title);
        assertThat(board.get(0).getContent()).isEqualTo(content);
        assertThat(board.get(0).getAuthor()).isEqualTo(author);
        assertThat(board.get(0).getViews()).isEqualTo(views);
        assertThat(board.get(0).getCategory().getCategoryName()).isEqualTo("자유게시판");
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
        final String newCategory = "new category";
        final LocalDateTime updatedAt = LocalDateTime.now();

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent, newCategory, updatedAt);

        //WHEN
        ResultActions result = mockMvc.perform(put(url, savedBoard.getBoardNo())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        //then
        result.andExpect(status().isOk());

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