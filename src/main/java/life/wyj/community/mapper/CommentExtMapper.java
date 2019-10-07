package life.wyj.community.mapper;

import life.wyj.community.model.Comment;
import life.wyj.community.model.CommentExample;
import life.wyj.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}