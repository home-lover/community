package mr_hong.community.service;

import mr_hong.community.enums.CommentTypeEnum;
import mr_hong.community.mapper.CommentMapper;
import mr_hong.community.mapper.QuestionMapper;
import mr_hong.community.model.Comment;
import mr_hong.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Transactional   //事务
    public boolean Insert(Comment comment) {
        if(comment.getType()!=null && comment.getParentId()!=null){
            if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
                //回复评论
                Comment dbComment = commentMapper.findByParentId(comment.getParentId());
                if(dbComment != null){
                    commentMapper.Insert(comment);
                    questionMapper.updateCommentCount(comment.getParentId());
                }else {
                    return false;
                }
            }else{
                //回复问题,comment.getType() == CommentTypeEnum.QUESTION.getType()
                Question question = questionMapper.getQuestionById(comment.getParentId());
                if (question != null){
                    commentMapper.Insert(comment);
                    questionMapper.updateCommentCount(comment.getParentId());
                }else {
                    return false;
                }
            }
        }else {
            return false;
        }
        return true;
    }
}
