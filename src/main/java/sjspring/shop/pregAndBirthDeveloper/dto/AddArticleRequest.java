package sjspring.shop.pregAndBirthDeveloper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;
import sjspring.shop.pregAndBirthDeveloper.domain.AttachedFile;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;
import sjspring.shop.pregAndBirthDeveloper.domain.User;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddArticleRequest {
    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    private String author;

    @NonNull
    private String category;

    private BoardCategory boardCategory;

    private AttachedFile attachedFile;

    private int views;

    private List<MultipartFile> file;

    private User user;

    public AddArticleRequest(@NotNull String category, @NotNull String title, @NotNull String content, List<MultipartFile> file) {
        this.category =category;
        this.title = title;
        this.content = content;
        this.file = file;
    }

    public AddArticleRequest(@NotNull String title, @NotNull String content, @NotNull String author, @NotNull String categoryName, BoardCategory boardCategory, int views) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = categoryName;
        this.boardCategory = boardCategory;
        this.views = views;
    }


    public Board toEntity(String username){
        Board board =  Board.builder()
                .title(title)
                .content(content)
                .views(views)
                .author(author)
                .email(username)
                .category(boardCategory)
                .user(user)
                .build();

        return board;
    }
}
