package life.wyj.community.advice;

import com.alibaba.fastjson.JSON;
import life.wyj.community.dto.ResultDto;
import life.wyj.community.exception.CustomizeErrorCode;
import life.wyj.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@ControllerAdvice
public class CustomizeEexceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response){
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResultDto resultDto = null;
            if(e instanceof CustomizeException){
                resultDto=  ResultDto.errorOf((CustomizeException) e);
            }else {
                resultDto=ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
            }


            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter  writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return  null;
        }else {

            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }
//else {
//                model.addAttribute("message", "233错误");
//            }
//            return new ModelAndView("error");
            return  null;
        }

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
