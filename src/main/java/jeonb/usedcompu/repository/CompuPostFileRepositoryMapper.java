package jeonb.usedcompu.repository;

import jeonb.usedcompu.model.CompuPostFile;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CompuPostFileRepositoryMapper {

    @Insert("insert into compupostfile(compupostid, writeremail, filename) " +
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
