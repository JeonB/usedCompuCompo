package thwjd.usedbook.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import thwjd.usedbook.entity.BookPostFile;

import java.util.List;

@Mapper
@Repository
public interface BookPostFileRepositoryMapper {

    @Insert("insert into bookpostfile(bookpostid, writeremail, filepath, filename) " +
            "values(#{bookPostId}, #{writerEmail}, #{filePath}, #{fileName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(BookPostFile bookPostFile);

    @Select("select * from bookpostfile where bookpostid = #{bookPostId}")
    List<BookPostFile> findById(Long bookPostId);

    @Select("select * from bookpostfile where bookpostid = #{bookPostId} limit 1")
    BookPostFile getOneFile(Long bookPostId);

    @Delete("delete from bookpostfile where bookpostid = #{bookPostId} and filename = #{fileName}")
    int removeFile(Long bookPostId, String fileName);

    @Delete("delete from bookpostfile where bookpostid = #{bookPostId}")
    int delete(Long bookPostId);
}
