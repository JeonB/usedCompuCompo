package jeonb.usedcompu.repository;

import java.util.List;
import jeonb.usedcompu.model.CompuPostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFileRepository extends JpaRepository<CompuPostFile,Long> {

    List<CompuPostFile> findAllByCompuPostId(Long compuPostId);
}
