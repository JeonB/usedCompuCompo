package jeonb.usedcompu.repository;

import jeonb.usedcompu.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentRepositoryMapper {
    @Insert("insert into comment(compupostid, reid, retype, writer, content, createtime) " +
            "values(#{compuPostId}, #{reid}, #{retype}, #{writer}, #{content}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void write(Comment comment);

    @Select("select * from comment where compupostid = #{compuPostId}")
    List<Comment> findAll(Long compuPostId);

    @Select("select * from comment where id = #{id}")
    Comment findById(Long id);

    @Delete("delete from comment where id = #{id}")
    int deleteById(Long id);

    @Update("update comment set content = #{content} where id = #{id}")
    int update(Long id, String content);
}
