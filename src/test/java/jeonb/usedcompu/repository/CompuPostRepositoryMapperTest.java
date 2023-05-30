package jeonb.usedcompu.repository;

import jeonb.usedcompu.model.CompuPost;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class CompuPostRepositoryMapperTest {

    @Autowired
    CompuPostRepositoryMapper repositoryMapper;

    @Test
    void save(){
        CompuPost compuPost = new CompuPost();
        compuPost.setWriterEmail("11@11");
        compuPost.setCompuName("11");
        compuPost.setCompuPrice(1111);
        compuPost.setCompuDescription("123456789");

        repositoryMapper.save(compuPost);
    }

    @Test
    void findAll(){
//        List<BookPost> all = repositoryMapper.findAll();
//        System.out.println("size: "+all.size());
//        Assertions.assertThat(all.size()).isEqualTo(1);
    }
}