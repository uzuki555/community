package life.wyj.community.mapper;

import life.wyj.community.dto.QuestionDTO;
import life.wyj.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into `question` (`title`,`description`,`gmt_create`,`gmt_modified`,`creator`,`tag`) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Results(id="questionResultMap",value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "title",column = "title"),
            @Result(property = "description",column = "description"),
            @Result(property = "tag",column = "tag"),
            @Result(property = "gmtCreate",column = "gmt_create"),
            @Result(property = "gmtModified",column = "gmt_modified"),
            @Result(property = "creator",column = "creator"),
            @Result(property = "viewCount",column = "view_count"),
            @Result(property = "likeCount",column = "like_count"),
            @Result(property = "commentCount",column = "comment_count"),
    })
    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();
    @Select("select * from question where creator =#{userId} limit #{offset},#{size} ")
    List<Question> listByCreator(@Param("userId")Integer userId, @Param("offset")Integer offset, @Param("size")Integer size);
    @Select("select count(1) from question where creator =#{userId}")
    Integer countByUserId(@Param("userId")Integer userId);

    @Select("select * from question where id =#{id}")
    Question findById(@Param("id")Integer id);
}
