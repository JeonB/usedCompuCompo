package jeonb.usedcompu.repository;

import jeonb.usedcompu.model.CompuPostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFileRepository extends JpaRepository<CompuPostFile,Long> {


}
