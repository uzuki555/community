package life.wyj.community.controller;

import life.wyj.community.dto.QuestionDTO;
import life.wyj.community.model.Question;
import life.wyj.community.model.User;
import life.wyj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller("publish")
public class PublishController {

    @Autowired
    public QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String publish(@PathVariable(name="id",required = false) Integer id,
                          Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id",questionDTO.getId());
//        System.out.println(questionDTO);
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPushlish(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("tag") String tag,
                             @RequestParam(name="id",required = true) Integer id,
                             HttpServletRequest request,
                             Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");

        if(user ==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setViewCount(0);
        question.setCommentCount(0);
        question.setLikeCount(0);
//        System.out.println(id);
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
