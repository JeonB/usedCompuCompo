package thwjd.usedbook.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import thwjd.usedbook.entity.Member;

import java.util.List;
import java.util.Optional;

class MemberRepositoryTest {

    Member member1 = new Member("test1@naver.com", "test1", "11");
    Member member2 = new Member("test2@naver.com", "test2", "22");
    MemberRepository memberRepository = new MemberRepository();
    Member save1 = memberRepository.save(member1);
    Member save2 = memberRepository.save(member2);

    @AfterEach
    void clear(){
        memberRepository.findAll().clear();
    }

    @Test
    void save(){
        List<Member> members = memberRepository.findAll();
        Assertions.assertThat(members).contains(save1, save2);
    }

    @Test
    void findById(){
        Member find = memberRepository.findById(2L);

        Assertions.assertThat(find).isSameAs(save2);
        //isSameAs는 주소값 비교
        //isEqualTo는 내용 비교
    }

    @Test
    void findByEmail(){
        Optional<Member> find1 = memberRepository.findByEmail("test2@naver.com");
        Optional<Member> find2 = memberRepository.findByEmail("test3@naver.com");

        Assertions.assertThat(find1).isNotEmpty();
        Assertions.assertThat(find2).isEmpty();
    }

}