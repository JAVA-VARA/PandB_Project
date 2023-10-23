package sjspring.shop.pregAndBirthDeveloper.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    Page<Board> findAll(Pageable pageable);

    @Modifying
    @Query("update board b set b.views = b.views+1 where b.boardNo = :boardNo")
    int updateView(Long boardNo);

}
