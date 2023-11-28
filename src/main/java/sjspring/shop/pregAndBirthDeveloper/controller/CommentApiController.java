package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.dto.CommentRequestDto;
import sjspring.shop.pregAndBirthDeveloper.dto.CommentResponseDto;
import sjspring.shop.pregAndBirthDeveloper.dto.UpdateCommentRequest;
import sjspring.shop.pregAndBirthDeveloper.service.BoardService;
import sjspring.shop.pregAndBirthDeveloper.service.CommentService;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentApiController {
    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    //등록
    @PostMapping("/api/articles/comment/{board_id}")
    public ResponseEntity<String> addComment(@PathVariable long board_id, @RequestPart String content, Principal principal) {
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail);
        Board board = boardService.findById(board_id);

        CommentRequestDto commentRequestDto = new CommentRequestDto(content, board, user);
        commentService.addComment(commentRequestDto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        String boardUrl = "/articles/" + board_id; // You need to modify this based on your URL structure

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boardUrl);
    }

    //수정
    @PutMapping("/api/articles/comment/{id}")
    public ResponseEntity<Long> updateComment(
            @PathVariable long id,
            @RequestPart String content){

        UpdateCommentRequest request = new UpdateCommentRequest(content);
        commentService.update(id, request);

        return ResponseEntity.ok(id);

    }

    //삭제
    @DeleteMapping("/api/articles/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable long commentId){
        commentService.delete(commentId);

        return ResponseEntity.ok()
                .build();
    }

//    @PostMapping("/api/articles/comment/{board_id}")
//    public ResponseEntity<List<CommentResponseDto>> addComment(@PathVariable long board_id, @RequestPart String content, Principal principal){
//        String userEmail = principal.getName();
//        User user = userService.findByEmail(userEmail);
//        Board board= boardService.findById(board_id);
//
//        CommentRequestDto commentRequestDto = new CommentRequestDto(content, board, user);
//        commentService.addComment(commentRequestDto);
//
//        List<CommentResponseDto> boardComments = boardService.findById(board_id).getCommentList()
//                .stream()
//                .map(CommentResponseDto::new)
//                .toList();
//
//        return ResponseEntity.ok()
//                .body(boardComments);
//    }
}
