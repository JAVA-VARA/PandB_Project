package sjspring.shop.pregAndBirthDeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentById(long id);

}
