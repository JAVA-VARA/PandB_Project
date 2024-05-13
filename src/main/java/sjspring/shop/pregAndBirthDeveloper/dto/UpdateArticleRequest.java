package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;
    private String category;
    private LocalDateTime updatedAt;
    private List<MultipartFile> files;

    public UpdateArticleRequest(String category, String title, String content, List<MultipartFile> files) {
        this.category =category;
        this.title = title;
        this.content = content;
        this.files = files;
    }

    public UpdateArticleRequest(String category, String title, String content, LocalDateTime updatedAt) {
        this.category =category;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
    }


}
