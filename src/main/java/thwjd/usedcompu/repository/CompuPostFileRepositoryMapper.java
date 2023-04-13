package thwjd.usedcompu.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import thwjd.usedcompu.entity.CompuPostFile;

import java.util.List;

@Mapper
@Repository
public interface CompuPostFileRepositoryMapper {

    @Insert("insert into compupostfile(compupostid, writeremail, filepath, filename) " +
            "values(#{compuPostId}, #{writerEmail}, #{filePath}, #{fileName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(CompuPostFile compuPostFile);

    @Select("select * from compupostfile where compupostid = #{compuPostId}")
    List<CompuPostFile> findById(Long compuPostId);

    @Select("select * from compupostfile where compupostid = #{compuPostId} limit 1")
    CompuPostFile getOneFile(Long compuPostId);

    @Delete("delete from compupostfile where compupostid = #{compuPostId} and filename = #{fileName}")
    int removeFile(Long compuPostId, String fileName);

    @Delete("delete from compupostfile where compupostid = #{compuPostId}")
    int delete(Long compuPostId);
}
