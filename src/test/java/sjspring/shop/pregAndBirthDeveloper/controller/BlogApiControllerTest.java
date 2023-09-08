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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.dto.AddArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardRepository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void mockMvcSetup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        boardRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공.")
    @Test
    public void addArticle() throws Exception{
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final String author = "author";
        final LocalDateTime createdAt = LocalDateTime.now();
        final LocalDateTime updatedAt = LocalDateTime.now();
        final Long views = 100L;

        final AddArticleRequest userRequest = new AddArticleRequest(title, content,author, createdAt, updatedAt, views);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when: 설정한 내용을 바탕으로 요청 전송...
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());
        List<Board> board = boardRepository.findAll();

        assertThat(board.size()).isEqualTo(1);
        assertThat(board.get(0).getTitle()).isEqualTo(title);
        assertThat(board.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception{
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final String author = "author";
        final LocalDateTime createdAt = LocalDateTime.now();
        final LocalDateTime updatedAt = LocalDateTime.now();
        final Long views = 100L;


        boardRepository.save(Board.builder()
                        .title(title)
                        .content(content)
                        .author(author)
                        .views(views)
                        .createdAt(createdAt)
                        .updatedAt(updatedAt)
                        .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }


    @DisplayName("findArticle: 블로그 글 조회에 성공한다")
    @Test
    public void findArticle() throws Exception{
        //given
        final String url = "/api/articles/{board_id}";
        final String title = "title";
        final String content = "content";
        final String author = "author";
        final LocalDateTime createdAt = LocalDateTime.now();
        final LocalDateTime updatedAt = LocalDateTime.now();
        final Long views = 100L;

        Board savedBoard = boardRepository.save(Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .views(views)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url, savedBoard.getBoardNo()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        //given
        final String url = "/api/articles/{board_id}";
        final String title = "title";
        final String content = "content";
        final String author = "author";
        final LocalDateTime createdAt = LocalDateTime.now();
        final LocalDateTime updatedAt = LocalDateTime.now();
        final Long views = 100L;

        Board savedBoard = boardRepository.save(Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .views(views)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build());

        //when
        mockMvc.perform(delete(url,savedBoard.getBoardNo()))
                .andExpect(status().isOk());

        //then
        List<Board> boards = boardRepository.findAll();
        assertThat(boards).isEmpty();
    }

    @DisplayName("deleteArticle: 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        //given
        final String url = "/api/articles/{board_id}";
        final String title = "title";
        final String content = "content";
        final String author = "author";
        final LocalDateTime createdAt = LocalDateTime.now();
        final LocalDateTime updatedAt = LocalDateTime.now();
        final Long views = 100L;

        Board savedBoard = boardRepository.save(Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .views(views)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build());

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        //WHEN
        ResultActions result = mockMvc.perform(put(url, savedBoard.getBoardNo())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        //then
        result.andExpect(status().isOk());

        Board board = boardRepository.findById(savedBoard.getBoardNo()).get();

        assertThat(board.getTitle()).isEqualTo(newTitle);
        assertThat(board.getContent()).isEqualTo(newContent);

    }



}