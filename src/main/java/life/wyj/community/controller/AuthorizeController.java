package life.wyj.community.controller;

import life.wyj.community.dto.AccessTokenDTO;
import life.wyj.community.dto.GithubUser;
import life.wyj.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {
    @Autowired
    public GithubProvider githubProvider;
    @Value("${github.client.id}")
    private  String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code") String code,
                            @RequestParam(name="state") String state,
                            HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        System.out.println(accessToken);
        GithubUser user = githubProvider.getUser(accessToken);
//        System.out.println(user.getId());
        if(user != null){
            request.getSession().setAttribute("user",user);
            System.out.println(user.getName());
            return "redirect:/";
        }else {
            return "redirect:/";
        }
//        return "index";
    }
}
