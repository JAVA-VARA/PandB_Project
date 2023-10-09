package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.dto.AddArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.FindArticle;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.service.BoardService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping("/api/articles") //api/articles로 post 요청이 들어오면 아래 메서드 실행.
    public ResponseEntity<Board> addArticle(@RequestBody AddArticleRequest request, Principal principal){
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
