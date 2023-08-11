package jeonb.usedcompu.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import jeonb.usedcompu.model.CompuCategory;
import jeonb.usedcompu.model.CompuPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Test
    void findById() {

    }

    @Test
    void findByWriterEmail() {
        String email = "test@email.com";

        CompuPost compuPost = postRepository.findByWriterEmail(email);
        assertEquals("testst", compuPost.getWriterEmail());
    }

    @Test
    void saveData() {
        CompuPost compuPost = new CompuPost();
        compuPost.setCompuName("포스트 게시물을 임의로 설정하는게 가당키나해?");
        compuPost.setWriterEmail("jeonb@gmail.com");
        compuPost.setCompuCategory(CompuCategory.RAM);
        compuPost.setCompuPrice(250000);
        compuPost.setCompuDescription("이게 맞나 싶어 ㅋㅋㅋ");
        Date currentTime = new Date();
        compuPost.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime));
        postRepository.save(compuPost);

    }
}