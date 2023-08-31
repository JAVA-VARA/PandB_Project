package sjspring.shop.pregAndBirthDeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no", updatable = false)
    private Long board_no;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

//    @Column(name = "views", nullable = false)
//    private Long views;
//
//    @Column(name = "created", nullable = false)
//    private Date created;
//
//    @Column(name = "updated", nullable = false)
//    private Date updated;


    @Builder
    public Board(String title, String content, Long views, Date created, Date updated){
        this.title = title;
        this.content = content;
//        this.views = views;
//        this.created = created;
//        this.updated = updated;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
