package sjspring.shop.pregAndBirthDeveloper.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "content")
    private String  content;

    @Column(name = "author")
    private String author;

    @Column(name = "email")
    private String email;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "board_no")
    @JsonBackReference
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_no")
    @JsonBackReference
    private User user;

    @Builder
    public Comment(String content, String author, String email, Board board, User user){
        this.content = content;
        this.author = author;
        this.email = email;
        this.board = board;
        this.user = user;
    }

    public void update(String content) {
        this.content = content;
    }

    public void updateAuthor(User user){
        this.user = user;
    }

    public void updatedBoard(Board board){
        this.board = board;
    }


    public boolean validateUser(User user){
        return !this.user.equals(user);
    }


}
