package thwjd.usedbook.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import thwjd.usedbook.entity.Comment;

import java.util.List;

@Mapper
@Repository
public interface CommentRepositoryMapper {
    @Insert("insert into comment(bookpostid, reid, retype, writer, content, createtime) " +
            "values(#{bookPostId}, #{reid}, #{retype}, #{writer}, #{content}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void write(Comment comment);

    @Select("select * from comment where bookpostid = #{bookPostId}")
    List<Comment> findAll(Long bookPostId);

    @Select("select * from comment where id = #{id}")
    Comment findById(Long id);

    @Delete("delete from comment where id = #{id}")
    int deleteById(Long id);

    @Update("update comment set content = #{content} where id = #{id}")
    int update(Long id, String content);
}
