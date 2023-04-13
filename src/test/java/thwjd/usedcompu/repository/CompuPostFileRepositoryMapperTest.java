package thwjd.usedcompu.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import thwjd.usedcompu.entity.CompuPostFile;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class CompuPostFileRepositoryMapperTest {

    @Autowired
    CompuPostFileRepositoryMapper repositoryMapper;

    @Test
    void save() {
        CompuPostFile compuPostFile = new CompuPostFile();
        compuPostFile.setWriterEmail("11@11");
        compuPostFile.setBookPostId(2L);
        compuPostFile.setFilePath("1212/1212.jpg");

        repositoryMapper.save(compuPostFile);
    }
}