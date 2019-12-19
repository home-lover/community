package mr_hong.community.controller;

import mr_hong.community.dto.NotificationDto;
import mr_hong.community.dto.PageDto;
import mr_hong.community.enums.NotificationTypeEnum;
import mr_hong.community.model.User;
import mr_hong.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name="id") Integer id){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }

        NotificationDto notificationDto = notificationService.read(id,user);
        if(notificationDto.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()||
                notificationDto.getType() == NotificationTypeEnum.REPLY_QUESTION.getType()){
            return "redirect:/question/" + notificationDto.getOuterId();
        }else {
            return "redirect:/";
        }
    }
}
