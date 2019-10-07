package life.wyj.community.mapper;

import life.wyj.community.model.Question;
import life.wyj.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incView(@Param("record") Question record);
    int incCommentCount(@Param("record") Question record);
    List<Question> selectRelated(Question question);
}