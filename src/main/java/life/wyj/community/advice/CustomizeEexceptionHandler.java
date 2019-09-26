package life.wyj.community.advice;

import life.wyj.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeEexceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle( Throwable e, Model model){

        String messgae = "服务不可用，请稍后再试";
        if(e instanceof CustomizeException){
            messgae=e.getMessage();
        }

        model.addAttribute("message",messgae);
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
