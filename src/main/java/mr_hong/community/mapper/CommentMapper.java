package mr_hong.community.mapper;

import mr_hong.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void Insert(Comment comment);

    @Select("select * from comment where parent_id = #{parentId}")
    Comment findByParentId(Integer parentId);

    @Select("select * from comment where parent_id = #{parentId} order by gmt_create desc")
    List<Comment> listByParentId(Integer id);
}
