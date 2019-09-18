package life.wyj.community.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data

public class Question {
    private Integer id;
    private  String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
}