package mr_hong.community.controller;

import mr_hong.community.cache.TagCache;
import mr_hong.community.dto.QuestionDto;
import mr_hong.community.mapper.QuestionMapper;
import mr_hong.community.model.Question;
import mr_hong.community.model.User;
import mr_hong.community.service.QuestionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    //通过问题页面进行问题编辑
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id") Integer id,Model model){
        QuestionDto question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }


    //发布问题页面请求
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("id") Integer id,
                            HttpServletRequest request, Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());

        //以下判断js里面也应该写
        if(title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "/publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","内容描述不能为空");
            return "/publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "/publish";
        }

        String inValid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(inValid)){
            model.addAttribute("error","无效标签："+inValid);
            return "/publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "/publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
