package thwjd.usedbook.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import thwjd.usedbook.entity.Member;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface MemberRepositoryMapper {

   //@Insert("insert into member(email, name, password) values(#{email}, #{name}, #{password})")
   //@Options(useGeneratedKeys = true, keyProperty = "id")
   //XmlMapper로 구현
   void save(Member member);

   //@Select("select * from member where id = #{findId}")
   //XmlMapper로 구현
   Member findById(Long findId);

   @Select("select * from member where email = #{email}")
   Optional<Member> findByEmail(String email);
   //optional이어야 loginservice에서 filter사용 가능

   @Select("select * from member")
   List<Member> findAll();

   //@Select("select * from member where email = #{email} and name = #{name}")
   //XmlMapper로 구현
   Optional<Member> findByEmailAndName(String email, String name);

   @Select("update member set name=#{name}, password=#{oldPassword} where email=#{email}")
   Integer update(Member member);

}
