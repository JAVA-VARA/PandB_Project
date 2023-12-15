package sjspring.shop.pregAndBirthDeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sjspring.shop.pregAndBirthDeveloper.domain.AttachedFile;

import java.util.List;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {

    List<AttachedFile> findAttachedFileByFileNo(Long fileNo);

    AttachedFile findAttachedFileByFileName(String fileName);
}
