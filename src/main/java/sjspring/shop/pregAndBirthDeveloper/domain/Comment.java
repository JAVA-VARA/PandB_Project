package sjspring.shop.pregAndBirthDeveloper.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import sjspring.shop.pregAndBirthDeveloper.dto.CommentRequestDto;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "content")
    private String  content;

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
    public Comment(String content, Board board, User user){
        this.content = content;
        this.board = board;
        this.user = user;
    }

    public void update(String content) {
        this.content = content;
    }


//대댓글 기능 나중에 추가할 예정
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "parent_id")
//    private Comment parent;
//
//    @OneToMany(mappedBy = "parent", orphanRemoval = true)
//    private List<Comment> children = new ArrayList<>();
//
//    @Column
//    @ColumnDefault("FALSE")
//    private boolean isDeleted;

//    public Comment(String content){
//        this.content = content;
//    }

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
