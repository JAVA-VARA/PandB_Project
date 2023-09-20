package sjspring.shop.pregAndBirthDeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no", updatable = false)
    private Long boardNo;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "views", nullable = false)
    private Long views;

    @Builder
    public Board(String title, String content,String author, Long views, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.title = title;
        this.content = content;
        this.author = author;
        this.views = views;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
