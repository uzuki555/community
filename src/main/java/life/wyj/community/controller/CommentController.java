package life.wyj.community.controller;

import life.wyj.community.dto.CommentCreateDTO;
import life.wyj.community.dto.CommentDTO;
import life.wyj.community.dto.ResultDto;
import life.wyj.community.enums.CommentTypeEnum;
import life.wyj.community.exception.CustomizeErrorCode;
import life.wyj.community.model.Comment;
import life.wyj.community.model.User;
import life.wyj.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");


        if(user ==null){
            return  ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreateDTO ==null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return  ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0L);
        commentService.insert(comment);

        Map<Object,Object> objectObjectMap = new HashMap<>();
//        objectObjectMap.put("message","成功");
        return ResultDto.successOf();
    };
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public  ResultDto comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOList = commentService.listBytargetId(id, CommentTypeEnum.COMMENT);
        return  ResultDto.successOf(commentDTOList);
    }
}
