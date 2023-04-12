package thwjd.usedbook.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import thwjd.usedbook.entity.BookPostFile;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class BookPostFileRepositoryMapperTest {

    @Autowired  BookPostFileRepositoryMapper repositoryMapper;

    @Test
    void save() {
        BookPostFile bookPostFile = new BookPostFile();
        bookPostFile.setWriterEmail("11@11");
        bookPostFile.setBookPostId(2L);
        bookPostFile.setFilePath("1212/1212.jpg");

        repositoryMapper.save(bookPostFile);
    }
}