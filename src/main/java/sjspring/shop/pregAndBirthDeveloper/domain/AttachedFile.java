package sjspring.shop.pregAndBirthDeveloper.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long fileNo;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;

    @Builder
    public AttachedFile(String originalFileName,String fileName, String filePath, Board board){
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.board = board;
    }


}
