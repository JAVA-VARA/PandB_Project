package sjspring.shop.pregAndBirthDeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Query("update board b set b.views = b.views+1 where b.boardNo = :boardNo")
    int updateView(Long boardNo);

}
