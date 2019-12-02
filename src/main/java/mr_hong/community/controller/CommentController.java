package mr_hong.community.controller;

import mr_hong.community.dto.CommentDto;
import mr_hong.community.dto.ResultDto;
import mr_hong.community.mapper.CommentMapper;
import mr_hong.community.model.Comment;
import mr_hong.community.model.User;
import mr_hong.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @ResponseBody      //@ResponseBody：能将后台的信息转化成json格式发送到前端。@RequestBody：能自动接收到前端json格式数据，并反序列化到对象中
    @RequestMapping (value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDto commentDto, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDto.errorOf(2002,"请先登录再评论！");
        }
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        //comment.setCommentator(1024);
        comment.setCommentator(user.getId());
        if(commentService.Insert(comment)==false){
            return ResultDto.errorOf(2003,"问题不存在了，换一个吧！");
        }
        return ResultDto.okOf();
    }
}
