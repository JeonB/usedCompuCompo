package thwjd.usedcompu.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import thwjd.usedcompu.entity.CompuPost;
import thwjd.usedcompu.entity.Pagination;

import java.util.List;

@Mapper
@Repository
public interface CompuPostRepositoryMapper {

    @Insert("insert into compupost(writeremail, compuname, compucategory, compuprice, compudescription, createtime) " +
                        "values(#{writerEmail}, #{compuName}, #{compuCategory}, #{compuPrice}, #{compuDescription}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(CompuPost compuPost);

    @Select("select count(*) from compupost where compucategory=#{category} ${search}")
    Integer findByCategoryCount(Pagination pagination);

    @Select("select * from compupost " +
            "where compucategory=#{category} ${search} " +
            "order by ${order} limit #{perRows} offset #{perFirstRow}")
        //$는 값만 반환, #은 ""을 포함하여 반환
    List<CompuPost> findByPaginationAndSearch(Pagination pagination);

    @Select("select count(*) from compupost ${search}")
    Integer findByAllCount(Pagination pagination);

    @Select("select * from compupost " +
            "${search} " +
            "order by ${order} limit #{perRows} offset #{perFirstRow}")
        //$는 값만 반환, #은 ""을 포함하여 반환
    List<CompuPost> findByAllPaginationAndSearch(Pagination pagination);

    @Select("select * from compupost where compucategory = #{category} order by createtime desc limit 10")
    List<CompuPost> getIndexList(String category);

    @Select("select * from compupost where id=#{id}")
    CompuPost findById(Long id);

    @Update("update compupost set viewcount = #{viewCount} where id=#{id}")
    void viewPlus(CompuPost compuPost);

    @Update("update compupost set compuname=#{compuName}, compucategory=#{compuCategory}, " +
            "compuprice=#{compuPrice}, compudescription=#{compuDescription} " +
            "where id=#{id}")
    int update(CompuPost compuPost);

    @Delete("delete from compupost where id = #{id}")
    int delete(Long compuPostId);
}
