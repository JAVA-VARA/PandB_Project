package sjspring.shop.pregAndBirthDeveloper.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;

import java.util.Optional;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {

    BoardCategory findByCategoryName(String categoryName);

    @NotNull Optional<BoardCategory> findById(@NotNull Long categoryId);


}
