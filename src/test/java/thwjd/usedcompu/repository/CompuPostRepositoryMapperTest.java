package thwjd.usedcompu.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import thwjd.usedcompu.entity.CompuPost;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class CompuPostRepositoryMapperTest {

    @Autowired
    CompuPostRepositoryMapper repositoryMapper;

    @Test
    void save(){
        CompuPost compuPost = new CompuPost();
        compuPost.setWriterEmail("11@11");
        compuPost.setBookName("11");
        compuPost.setBookPrice(1111);
        compuPost.setBookDescription("123456789");

        repositoryMapper.save(compuPost);
    }

    @Test
    void findAll(){
//        List<BookPost> all = repositoryMapper.findAll();
//        System.out.println("size: "+all.size());
//        Assertions.assertThat(all.size()).isEqualTo(1);
    }
}