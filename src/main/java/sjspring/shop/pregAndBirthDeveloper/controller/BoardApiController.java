package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.AddArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.FindArticle;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.service.BoardService;
import sjspring.shop.pregAndBirthDeveloper.service.CategoryService;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardApiController {
    private final BoardService boardService;
    private final UserService userService;
    private final CategoryService categoryService;


    @PostMapping("/api/articles")
    public ResponseEntity<Board> addArticle(@RequestBody AddArticleRequest request, Principal principal){

        request.setBoardCategory(categoryService.save(request.getCategory()));

        String email = principal.getName();
        User user = userService.findByEmail(email);
        String author = user.getNickName();
        request.setAuthor(author);

        Board savedArticle = boardService.save(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<FindArticle>> findAllArticles(){
        List<FindArticle> articles = boardService.findAll()
                .stream()
                .map(FindArticle::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{board_id}")
    public ResponseEntity<FindArticle> findArticle(@PathVariable long board_id){
        Board board = boardService.findById(board_id);
        return ResponseEntity.ok()
                .body(new FindArticle(board));
    }

    @PutMapping("/api/articles/{board_id}")
    public ResponseEntity<Board> updateArticle(@PathVariable long board_id, @RequestBody UpdateArticleRequest request){
        Board updatedArticle = boardService.update(board_id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }

    @DeleteMapping("/api/articles/{board_id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long board_id){
        boardService.delete(board_id);

        return ResponseEntity.ok()
                .build();
    }
}

