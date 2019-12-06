package mr_hong.community.controller;

import mr_hong.community.dto.CommentDto;
import mr_hong.community.dto.QuestionDto;
import mr_hong.community.mapper.QuestionMapper;
import mr_hong.community.model.Question;
import mr_hong.community.service.CommentService;
import mr_hong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model){
        //增加阅读数
        questionService.incView(id);
        QuestionDto questionDto = questionService.getById(id);
        List<CommentDto> comments = commentService.ListByQuestionId(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        return "question";
    }
}
