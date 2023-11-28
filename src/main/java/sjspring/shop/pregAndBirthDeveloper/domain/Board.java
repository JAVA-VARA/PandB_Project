package sjspring.shop.pregAndBirthDeveloper.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EntityListeners(AuditingEntityListener.class)
@Entity(name = "board")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no", updatable = false)
    private Long boardNo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "views", columnDefinition = "integer default 0", nullable = false)
    private int views;

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_category_id")
    @JsonBackReference
    private BoardCategory category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<AttachedFile> attachedFileList = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER , cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    @JsonManagedReference
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Board(String title, String content, String author, int views, String email, BoardCategory category){
        this.title = title;
        this.content = content;
        this.author = author;
        this.views = views;
        this.email = email;
        this.category = category;
    }

    public void mappingAttachedFileToBoard(AttachedFile attachedFile){
        if(this.attachedFileList == null){
            this.attachedFileList = new ArrayList<>();
        }
        this.attachedFileList.add(attachedFile);
    }

    public void mappingCommentToBoard(Comment comment){
        if(this.commentList == null){
            this.commentList = new ArrayList<>();
        }
        this.commentList.add(comment);
    }

    public void update(String title, String content, BoardCategory boardCategory) {
        this.title = title;
        this.content = content;
        this.category = boardCategory;
    }

    public void update(int views) {
        this.views = views;
    }

    public void deleteCommentInBoard(Comment comment){
        this.commentList.remove(comment);
    }
}
