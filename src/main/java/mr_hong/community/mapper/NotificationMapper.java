package mr_hong.community.mapper;

import mr_hong.community.model.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification(notifier,receiver,outer_id,type,gmt_create,status,notifier_name,notify_title)values(#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{notifyTitle})")
    void Insert(Notification notification);

    @Select("select count(1) from notification where receiver = #{userId}")
    Integer count(Integer userId);

    @Select("select * from notification where receiver = #{userId} order by gmt_create desc limit #{offset},#{size}")
    List<Notification> listByUserId(Integer userId, Integer offset, Integer size);

    @Select("select count(1) from notification where receiver = #{userId} and status = 0")
    Integer unreadCount(Integer receiveId);

    @Select("select * from notification where id = #{id}")
    Notification findByNotificationId(Integer id);

    @Update("update notification set status = #{status} where id = #{id}")
    void update(Notification notification);
}
