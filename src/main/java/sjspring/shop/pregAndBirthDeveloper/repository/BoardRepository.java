package sjspring.shop.pregAndBirthDeveloper.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sjspring.shop.pregAndBirthDeveloper.domain.Board;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;
import sjspring.shop.pregAndBirthDeveloper.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByBoardNo(Long boardNo);

    Page<Board> findAll(Pageable pageable);

    Page<Board> findByCategory_Id(Long categoryId, Pageable pageable);
    Page<Board> findByViewsGreaterThanEqual (int views, Pageable pageable);

    public List<Board> findByCategory(BoardCategory category);
    List<Comment> findCommentListByBoardNo(Long boardNo);

    @Modifying
    @Query("update board b set b.views = b.views+1 where b.boardNo = :boardNo")
    int updateView(Long boardNo);

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);

}
