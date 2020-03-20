package life.wyj.community.controller;

import life.wyj.community.dto.CommentDTO;
import life.wyj.community.dto.QuestionDTO;
import life.wyj.community.enums.CommentTypeEnum;
import life.wyj.community.service.CommentService;
import life.wyj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
                           Model model){

        //累加阅读数
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relateQuestions = questionService.selectRelated(questionDTO);
        questionService.incView(id);
        List<CommentDTO> commentDTOList =  commentService.listBytargetId(id, CommentTypeEnum.QUESTION);

        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOList);
        model.addAttribute("relateQuestions",relateQuestions);
        return "question";
    }
}
