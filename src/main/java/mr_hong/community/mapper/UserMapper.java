package mr_hong.community.mapper;

import mr_hong.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}") //第二个token会从下文的token里去找，自动识别
    User findByToken(@Param("token") String token);  //因为不是上文Insert的User user所以需要@param识别

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);
}
