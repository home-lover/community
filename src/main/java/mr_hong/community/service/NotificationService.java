package mr_hong.community.service;

import mr_hong.community.dto.NotificationDto;
import mr_hong.community.dto.PageDto;
import mr_hong.community.dto.QuestionDto;
import mr_hong.community.enums.NotificationTypeEnum;
import mr_hong.community.mapper.NotificationMapper;
import mr_hong.community.mapper.UserMapper;
import mr_hong.community.model.Notification;
import mr_hong.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDto listByUserId(Integer userId, Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        Integer totalCount = notificationMapper.count(userId);
        Integer totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size +1;
        }
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        pageDto.setPagination(totalPage,page);

        //分页实现,每一次都会从前端获取一个page,到ProfileController更新page查询。
        Integer offset = size*(page-1);
        List<Notification> notifications = notificationMapper.listByUserId(userId,offset,size);
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        //将notification里的每个属性对应到notificationDto
        for (Notification notification : notifications) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            notificationDto.setNotifyTitle(notification.getNotifyTitle());
            User user = userMapper.findById(notification.getNotifier());
            notificationDto.setNotifier(user);
            notificationDto.setType(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDtoList.add(notificationDto);
        }
        pageDto.setNotifications(notificationDtoList);
        return pageDto;
    }

    public Integer unreadCount(Integer receiveId) {
        Integer unreadCount = notificationMapper.unreadCount(receiveId);
        return unreadCount;
    }
}
