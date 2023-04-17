package jeonb.usedcompu.repository;

import jeonb.usedcompu.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Optional;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class MemberRepositoryMapperTest {
    @Autowired MemberRepositoryMapper mapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void save() {
    }

    @Test
    void findById() {
        Member find = mapper.findById(1L);
        System.out.println(find);
        Assertions.assertThat(find).isNotNull();
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByEmailAndName() {
        Optional<Member> find = mapper.findByEmailAndName("11@11", "11");
        System.out.println(find);
    }
}