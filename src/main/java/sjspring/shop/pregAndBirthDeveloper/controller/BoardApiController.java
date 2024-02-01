package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.AddArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.FindArticle;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateArticleRequest;
import sjspring.shop.pregAndBirthDeveloper.service.BoardService;
import sjspring.shop.pregAndBirthDeveloper.service.CategoryService;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardApiController {
    private final BoardService boardService;
    private final UserService userService;
    private final CategoryService categoryService;

    //기본 글 저장 로직 + 첨부파일
    @PostMapping(value = "/api/articles", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Board> addArticle(
            @RequestParam String category,
            @RequestParam String title,
            @RequestParam String content,
//            @RequestPart(required = false) MultipartFile files, => 단일 파일 저장.
            @RequestPart(name = "files", required = false) List<MultipartFile> files,
            Principal principal) throws IOException, InterruptedException {
        AddArticleRequest request = new AddArticleRequest(category, title, content, files);

        //카테고리 저장.
        request.setBoardCategory(categoryService.save(category));

        String email = principal.getName();
        User user = userService.findByEmail(email);
        String author = user.getNickname();
        request.setAuthor(author);
        request.setUser(user);

        Board savedArticle = boardService.save(request, email);

        user.mappingBoardToUser(savedArticle);

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
    public ResponseEntity<Board> updateArticle(@PathVariable long board_id,
                                               @RequestParam String category,
                                               @RequestParam String title,
                                               @RequestParam String content,
                                               @RequestPart(required = false) MultipartFile files) throws IOException {

        UpdateArticleRequest request = new UpdateArticleRequest(category, title, content, files);


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

    @GetMapping("/api/articles/scrap/{board_id}")
    public ResponseEntity<Void> scrapArticle(@PathVariable long board_id, Principal principal){

        String userEmail = principal.getName();
        boardService.scrap(board_id, userEmail);

        return ResponseEntity.ok()
                .build();
    }
}

//    @PutMapping("/api/articles/{board_id}")
//    public ResponseEntity<Board> updateArticle(@PathVariable long board_id,
//                                               @RequestBody UpdateArticleRequest request){
//
//        Board updatedArticle = boardService.update(board_id, request);
//
//        return ResponseEntity.ok()
//                .body(updatedArticle);
//    }

//기본 글 저장 로직
//    @PostMapping("/api/articles")
//    public ResponseEntity<Board> addArticle(@RequestBody AddArticleRequest request, Principal principal){
//
//        request.setBoardCategory(categoryService.save(request.getCategory()));
//
//        String email = principal.getName();
//        User user = userService.findByEmail(email);
//        String author = user.getNickName();
//        request.setAuthor(author);
//
//        Board savedArticle = boardService.save(request, principal.getName());
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(savedArticle);
//    }

//기본 글 저장 로직 + 첨부파일 #1
//    @PostMapping("/api/articles")
//    public ResponseEntity<Board> addArticle(
//            AddArticleRequest request,
////            @RequestBody AddArticleRequest request,
////            MultipartHttpServletRequest files,
//            MultipartFile files,
//            Principal principal) throws IOException
//
//    {
//        //글 저장
//        request.setBoardCategory(categoryService.save(request.getCategory()));
//
//        String email = principal.getName();
//        User user = userService.findByEmail(email);
//        String author = user.getNickName();
//        request.setAuthor(author);
//
//
//        Board savedArticle = boardService.save(request, principal.getName(), files);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(savedArticle);
//    }

