package mr_hong.community.service;

import mr_hong.community.dto.CommentDto;
import mr_hong.community.enums.CommentTypeEnum;
import mr_hong.community.mapper.CommentMapper;
import mr_hong.community.mapper.QuestionMapper;
import mr_hong.community.mapper.UserMapper;
import mr_hong.community.model.Comment;
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

    @Transactional   //事务
    public boolean Insert(Comment comment) {
        if(comment.getType()!=null && comment.getParentId()!=null){
            if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
                //回复评论
                Comment dbComment = commentMapper.findByParentId(comment.getParentId());
                if(dbComment != null){
                    commentMapper.Insert(comment);
                    dbComment.setCommentCount(1);
                    commentMapper.updateCommentCount(dbComment);
                }else {
                    return false;
                }
            }else{
                //回复问题,comment.getType() == CommentTypeEnum.QUESTION.getType()
                Question question = questionMapper.getQuestionById(comment.getParentId());
                if (question != null){
                    commentMapper.Insert(comment);
                    question.setCommentCount(1);
                    questionMapper.updateCommentCount(question);
                }else {
                    return false;
                }
            }
        }else {
            return false;
        }
        return true;
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
