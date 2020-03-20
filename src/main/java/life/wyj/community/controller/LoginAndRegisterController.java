package life.wyj.community.controller;

import life.wyj.community.model.User;
import life.wyj.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Controller
public class LoginAndRegisterController {
    @Autowired
    public UserService userService;
    @GetMapping("/toLoginAndRegisterPage")
    public String toLoginPage(){
        return "loginPage";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null );
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return "redirect:/";
    }
    @PostMapping("/login")
    public String login(@RequestParam(value = "username" ) String username,
                        @RequestParam(value = "password") String password ,
                        HttpServletResponse response,
                        Model model){
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        User loginUser = userService.login(user);
        if(loginUser !=null){
            response.addCookie(new Cookie("token",loginUser.getToken()));
        }else {
            model.addAttribute("error","用户名或密码错误");
            return "loginPage";
        }
        return "redirect:/";
    }
    @GetMapping("/toRegisterPage")
    public String toRegisterPage(){
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam(value = "username" ) String username,
                        @RequestParam(value = "password") String password ,
                        HttpServletResponse response,
                        Model model){
        User user = new User();
        user.setName(username);
        user.setPassword(password);

        String Token = userService.register(user);
        if(Token !=null){
            response.addCookie(new Cookie("token",Token));
        }else {
            model.addAttribute("error","用户名已存在");
            return "register";
        }
        return "redirect:/";
    }
}
