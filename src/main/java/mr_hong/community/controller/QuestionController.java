package mr_hong.community.controller;

import mr_hong.community.dto.CommentDto;
import mr_hong.community.dto.QuestionDto;
import mr_hong.community.dto.ResultDto;
import mr_hong.community.mapper.QuestionMapper;
import mr_hong.community.model.Question;
import mr_hong.community.model.User;
import mr_hong.community.service.CommentService;
import mr_hong.community.service.NotificationService;
import mr_hong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model, HttpServletRequest request){
        //增加阅读数
        questionService.incView(id);
        QuestionDto questionDto = questionService.getById(id);
        List<QuestionDto> relatedQuestions = questionService.selectRelated(questionDto);
        List<CommentDto> comments = commentService.ListByQuestionId(id);

        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }


}
