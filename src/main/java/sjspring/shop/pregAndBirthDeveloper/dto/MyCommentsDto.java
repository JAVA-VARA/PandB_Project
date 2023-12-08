package sjspring.shop.pregAndBirthDeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;
import sjspring.shop.pregAndBirthDeveloper.domain.User;

import java.util.List;
@Getter
@NoArgsConstructor
public class MyCommentsDto {

        private User user;
        private List<Comment> commentList;

        public MyCommentsDto(User user, List<Comment> commentList){
            this.user = user;
            this.commentList = commentList;
        }

    public MyCommentsDto(User user){
        this.user = user;
    }


}
