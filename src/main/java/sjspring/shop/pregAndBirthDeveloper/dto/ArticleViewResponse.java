package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sjspring.shop.pregAndBirthDeveloper.domain.AttachedFile;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ArticleViewResponse {
    private Long boardNo;
    private String title;
    private String content;
    private String author;
    private int views;
    private BoardCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AttachedFile> attachedFileList;
    private List<Comment> commentList;




    public ArticleViewResponse(Board board){
        this.boardNo = board.getBoardNo();
        this.title = board.getTitle();
        this.author = board.getAuthor();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.views = board.getViews();
        this.attachedFileList = board.getAttachedFileList();
        this.commentList = board.getCommentList();
    }


}
