package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.*;
import sjspring.shop.pregAndBirthDeveloper.domain.AttachedFile;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttachedFileDto {
    private String originalFileName;
    private String fileName;
    private String filePath;
    private String imageUrl;
    private Board board;


    public AttachedFile toEntity() {

        return AttachedFile.builder()
                .originalFileName(originalFileName)
                .fileName(fileName)
                .filePath(filePath)
                .imageUrl(imageUrl)
                .board(board)
                .build();
    }
    public AttachedFileDto(String originalFileName, String fileName, String filePath){
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public AttachedFileDto(String originalFileName, String fileName, String filePath, String imageUrl){
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.imageUrl = imageUrl;
    }
}
