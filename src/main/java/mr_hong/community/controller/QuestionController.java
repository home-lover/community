package mr_hong.community.controller;

import mr_hong.community.dto.QuestionDto;
import mr_hong.community.mapper.QuestionMapper;
import mr_hong.community.model.Question;
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
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model){
        QuestionDto questionDto = questionService.getById(id);
        model.addAttribute("question",questionDto);
        return "question";
    }
}
