package sjspring.shop.pregAndBirthDeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.ScrapArticle;
import sjspring.shop.pregAndBirthDeveloper.domain.User;

public interface ScrapArticlesRepository extends JpaRepository<ScrapArticle, Long> {
}
