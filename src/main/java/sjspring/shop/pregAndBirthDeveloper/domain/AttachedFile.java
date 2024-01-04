package sjspring.shop.pregAndBirthDeveloper.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_no")
    @JsonBackReference
    private Board board;

    @Builder
    public AttachedFile(String originalFileName,String fileName, String filePath, Board board, String imageUrl){
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.board = board;
        this.imageUrl = imageUrl;
    }
}
