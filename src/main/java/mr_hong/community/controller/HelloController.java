package mr_hong.community.controller;

import mr_hong.community.dto.PageDto;
import mr_hong.community.dto.QuestionDto;
import mr_hong.community.mapper.QuestionMapper;
import mr_hong.community.mapper.UserMapper;
import mr_hong.community.model.Question;
import mr_hong.community.model.User;
import mr_hong.community.service.NotificationService;
import mr_hong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HelloController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search,
                        HttpServletRequest request){

        if(search != null){
            PageDto pagination = questionService.listQuestionByKeyWord(search,page,size);
           model.addAttribute("pagination",pagination);
           model.addAttribute("search",search);
        }
        if(search==null || search==""){
            PageDto pagination = questionService.list(page,size);
           model.addAttribute("pagination",pagination);
        }
        return "index"; //会去找hello这个HTML文件
    }

    @GetMapping("/questionKeyWrd")
    public String getQuestionByKeyWord(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search,
                        HttpServletRequest request){
        if(search == null){
            return "index"; //会去找hello这个HTML文件
        }

        return "index"; //会去找hello这个HTML文件
    }
}
