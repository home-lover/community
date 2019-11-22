package mr_hong.community.advice;

import mr_hong.community.exception.CustomizeException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;



/*@ControllerAdvice
public class CustomizeExceptionHandle {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,Throwable e,Model model){
        HttpStatus status = getStatus(request);
        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else{
            model.addAttribute("message","服务已经冒烟了！稍后再试试~~");
        }
        return new ModelAndView("error");
    }
     private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
     }
}*/

