package sjspring.shop.pregAndBirthDeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sjspring.shop.pregAndBirthDeveloper.domain.AttachedFile;

import java.util.List;

@Repository
public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {

    List<AttachedFile> findAttachedFileByFileNo(Long fileNo);


}
