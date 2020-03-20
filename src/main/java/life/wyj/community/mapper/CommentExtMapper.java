package life.wyj.community.mapper;

import life.wyj.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}