package sjspring.shop.pregAndBirthDeveloper.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCategory {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(unique = true)
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Board> boardList = new ArrayList<>();

    public BoardCategory(String categoryName){
        this.categoryName = categoryName;
    }


    public void mappingBoard(Board board){
        if (this.boardList == null) {
            this.boardList = new ArrayList<>();
        }
        this.boardList.add(board);
    }

    public void deleteBoard(Board board){
        this.boardList.remove(board);
    }
}
