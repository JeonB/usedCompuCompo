package jeonb.usedcompu.repository;

import java.util.Optional;
import jeonb.usedcompu.entity.CompuPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<CompuPost,Long> {

    @Override
    Optional<CompuPost> findById(Long aLong);

    CompuPost findByWriterEmail(String email);

}
