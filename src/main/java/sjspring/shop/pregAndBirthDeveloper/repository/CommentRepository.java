package sjspring.shop.pregAndBirthDeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;

@Repository

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentById(long id);

}
