package sjspring.shop.pregAndBirthDeveloper.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "scrap")
@Getter
@Setter
public class ScrapArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_no")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_no")
    @JsonBackReference
    private Board board;

    @Builder
    public ScrapArticle(User user, Board board){
        this.user = user;
        this.board = board;
    }

    public ScrapArticle() {

    }
}
