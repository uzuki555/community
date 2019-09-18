package life.wyj.community.mapper;

import life.wyj.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select  *  from user where token=#{token}")
    @Results(id="userResultMap",value = {
            @Result(property = "id",column = "id",id=true),
            @Result(property = "accountId",column = "account_id"),
            @Result(property = "name",column = "name"),
            @Result(property = "token",column = "token"),
            @Result(property = "gmtCreate",column = "gmt_create"),
            @Result(property = "gmtModified",column = "gmt_modified"),
            @Result(property = "avatarUrl",column = "avatar_url")
    })
    User findByToken(@Param("token") String token);
    @Select("select *  from user where id = #{id}")
    @ResultMap("userResultMap")
    User findById(@Param("id")Integer creator);
}
