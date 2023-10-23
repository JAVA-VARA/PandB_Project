package sjspring.shop.pregAndBirthDeveloper.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;


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


    @Builder
    public Board(String title, String content, String author, int views, String email, BoardCategory category){
        this.title = title;
        this.content = content;
        this.author = author;
        this.views = views;
        this.email = email;
        this.category = category;
    }

    public void update(String title, String content, BoardCategory boardCategory) {
        this.title = title;
        this.content = content;
        this.category = boardCategory;
    }

    public void update(int views) {
        this.views = views;
    }
}
