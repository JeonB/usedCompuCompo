package thwjd.usedbook.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import thwjd.usedbook.entity.BookCategory;
import thwjd.usedbook.entity.BookPost;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class BookPostRepositoryMapperTest {

    @Autowired BookPostRepositoryMapper repositoryMapper;

    @Test
    void save(){
        BookPost bookPost = new BookPost();
        bookPost.setWriterEmail("11@11");
        bookPost.setBookName("11");
        bookPost.setBookPrice(1111);
        bookPost.setBookDescription("123456789");

        repositoryMapper.save(bookPost);
    }

    @Test
    void findAll(){
//        List<BookPost> all = repositoryMapper.findAll();
//        System.out.println("size: "+all.size());
//        Assertions.assertThat(all.size()).isEqualTo(1);
    }
}