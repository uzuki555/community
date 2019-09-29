package life.wyj.community.controller;

import life.wyj.community.dto.CommentDTO;
import life.wyj.community.dto.ResultDto;
import life.wyj.community.exception.CustomizeErrorCode;
import life.wyj.community.exception.CustomizeException;
import life.wyj.community.mapper.CommentMapper;
import life.wyj.community.model.Comment;
import life.wyj.community.model.User;
import life.wyj.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");


        if(user ==null){
            return  ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        Map<Object,Object> objectObjectMap = new HashMap<>();
//        objectObjectMap.put("message","成功");
        return ResultDto.successOf();
    };
}
