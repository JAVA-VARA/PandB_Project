package sjspring.shop.pregAndBirthDeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.CommentRequestDto;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardCategoryRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.CommentRepository;
import sjspring.shop.pregAndBirthDeveloper.repository.UserRepository;
import sjspring.shop.pregAndBirthDeveloper.service.BoardService;
import sjspring.shop.pregAndBirthDeveloper.service.CategoryService;
import sjspring.shop.pregAndBirthDeveloper.service.CommentService;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardCategoryRepository boardCategoryRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    CommentService commentService;

    @Autowired
    CategoryService categoryService;

    User user;

    @BeforeEach
    void setSecurityContext() {
        userRepository.deleteAll();
        boardRepository.deleteAll();
        commentRepository.deleteAll();

        user = userRepository.save(User.builder()
            .email("test@gmail.com")
            .name("name")
            .hp("12345678")
            .nickname("author")
            .password("Test")
            .build());


        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }



    @Test
    @DisplayName("addComment: 댓글 추가 성공.")
    void addComment() throws Exception {
        //GIVEN
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("name");

        Board savedboard = createDefaultArticleAndCategoryMapping();

        //when
        ResultActions resultActions =  mockMvc.perform(post("/api/articles/comment/{board_id}", savedboard.getBoardNo())
                .param("content","Test content")
                .principal(principal));


        //then
        resultActions.andExpect(status().isCreated());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList.size()).isEqualTo(1);
        assertThat(commentList.get(0).getContent()).isEqualTo("Test content");
    }

    @Test
    void updateComment() throws Exception {
        //GIVEN
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("name");

        Board savedboard = createDefaultArticleAndCategoryMapping();
        CommentRequestDto commentRequestDto = new CommentRequestDto("content", "author", "email", savedboard, user);
        Comment comment = commentService.addComment(commentRequestDto);

        //when
        ResultActions resultActions =  mockMvc.perform(put("/api/articles/comment/{id}", comment.getId())
                .param("content","comment changed")
                .principal(principal));

        //then
        resultActions.andExpect(status().isOk());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList.size()).isEqualTo(1);
        assertThat(commentList.get(0).getContent()).isEqualTo("comment changed");


    }

    @Test
    void deleteComment() throws Exception {
        // GIVEN
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("name");
        Board savedboard = createDefaultArticleAndCategoryMapping();

        //comment 생성
        CommentRequestDto commentRequestDto = new CommentRequestDto("content", "author", "email", savedboard, user);
        Comment comment = commentService.addComment(commentRequestDto);

        //when-then
        mockMvc.perform(delete("/api/articles/comment/{commentId}",comment.getId() ))
                .andExpect(status().isOk());
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


        return board;
    }
    //카테고리 저장 여부 확인 메서드
    private BoardCategory createDefalutCategory() {
        String categoryName = "정보";
        return categoryService.save(categoryName);
    }
}