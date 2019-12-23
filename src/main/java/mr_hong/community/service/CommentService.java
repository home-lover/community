package mr_hong.community.service;

import mr_hong.community.dto.CommentDto;
import mr_hong.community.enums.CommentTypeEnum;
import mr_hong.community.enums.NotificationStatusEnum;
import mr_hong.community.enums.NotificationTypeEnum;
import mr_hong.community.mapper.CommentMapper;
import mr_hong.community.mapper.NotificationMapper;
import mr_hong.community.mapper.QuestionMapper;
import mr_hong.community.mapper.UserMapper;
import mr_hong.community.model.Comment;
import mr_hong.community.model.Notification;
import mr_hong.community.model.Question;
import mr_hong.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional   //事务
    public boolean Insert(Comment comment, User commentator) {
        if(comment.getType()!=null && comment.getParentId()!=null){
            if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
                //回复评论
                Comment dbComment = commentMapper.findByParentId(comment.getParentId());
                if(dbComment != null){
                    commentMapper.Insert(comment);
                    dbComment.setCommentCount(1);
                    commentMapper.updateCommentCount(dbComment);
                    Question question = questionMapper.getQuestionById(dbComment.getParentId());
                    createNotify(comment,dbComment.getCommentator(),commentator.getName(),question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
                }else {
                    return false;
                }
            }else{
                //回复问题,comment.getType() == CommentTypeEnum.QUESTION.getType()
                Question question = questionMapper.getQuestionById(comment.getParentId());
                if (question != null){
                    comment.setCommentCount(0);
                    commentMapper.Insert(comment);
                    question.setCommentCount(1);
                    questionMapper.updateCommentCount(question);
                    createNotify(comment, question.getCreator(),commentator.getName(),question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
                }else {
                    return false;
                }
            }
        }else {
            return false;
        }
        return true;
    }

    private void createNotify(Comment comment, Integer receiver, String notifyName, String notifyTitle, NotificationTypeEnum notificationTypeEnum, Integer outerId) {
        //自己回复自己不需要提示通知
        if(receiver == comment.getCommentator()){
            return;
        }
        Notification notification = new Notification();
        notification.setType(notificationTypeEnum.getType());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());
        notification.setOuterId(outerId);
        notification.setReceiver(receiver);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setNotifierName(notifyName);
        notification.setNotifyTitle(notifyTitle);
        notificationMapper.Insert(notification);
    }

    public List<CommentDto> ListByQuestionId(Integer id) {
        List<Comment> comments = commentMapper.listByParentId(id);
        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment comment:comments){
            User user = userMapper.findById(comment.getCommentator());
            CommentDto commentDto = new CommentDto();
            if(comment.getType() == CommentTypeEnum.QUESTION.getType()){
                BeanUtils.copyProperties(comment,commentDto);
                commentDto.setUser(user);
                commentDtos.add(commentDto);
            }
        }
        return commentDtos;
    }

    public List<CommentDto> ListByTargetId(Integer id) {
        List<Comment> comments = commentMapper.listByParentId(id);
        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment comment:comments){
            User user = userMapper.findById(comment.getCommentator());
            CommentDto commentDto = new CommentDto();
            if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
                BeanUtils.copyProperties(comment,commentDto);
                commentDto.setUser(user);
                commentDtos.add(commentDto);
            }
        }
        return commentDtos;
    }
}
