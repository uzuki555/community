package life.wyj.community.controller;

import life.wyj.community.dto.AccessTokenDTO;
import life.wyj.community.dto.GithubUser;
import life.wyj.community.mapper.UserMapper;
import life.wyj.community.model.User;
import life.wyj.community.provider.GithubProvider;
import life.wyj.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    public UserMapper userMapper;

    @Autowired
    public GithubProvider githubProvider;
    @Value("${github.client.id}")
    private  String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code") String code,
                            @RequestParam(name="state") String state,
                            HttpServletRequest request ,
                            HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        System.out.println(accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
       System.out.println(githubUser);
        if(githubUser != null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            if(githubUser.getName()==null&&githubUser.getName()==""){
                user.setName("不愿意透露姓名的老司机");
            }else {
                user.setName(githubUser.getName());
            }

            user.setAccountId(String.valueOf(githubUser.getId()));

            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",user.getToken()));

            System.out.println(githubUser.getName());
            return "redirect:/";
        }else {
            return "redirect:/";
        }
//        return "index";
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
}
