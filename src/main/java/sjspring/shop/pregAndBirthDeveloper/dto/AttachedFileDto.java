package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.*;
import sjspring.shop.pregAndBirthDeveloper.domain.AttachedFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttachedFileDto {
    private String originalFileName;
    private String fileName;
    private String filePath;


    public AttachedFile toEntity() {

        return AttachedFile.builder()
                .originalFileName(originalFileName)
                .fileName(fileName)
                .filePath(filePath)
                .build();
    }

    @Builder
    public AttachedFileDto(String originalFileName, String fileName, String filePath){
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

}
