package life.wyj.community.exception;

public enum CustomizeErrorCode implements  ICustomizeErrorCode {
    QUESTION_NOT_FOUND("找不到问题，换个问题试试",2001 ),
    TARGET_PARAM_NOT_FOUND("未选中任何问题或者评论回复",2002),
    NO_LOGIN("未登录不能进行操作，请登陆后重试",2003),
    SYS_ERROR("服务不可用，请稍后再试",2004),
    TYPE_PARAM_WRONG("评论类型错误或不存在",2005),
    COMMENT_NOT_FOUND("您回复的评论不存在了，请换回复",2006),
    CONTENT_IS_EMPTY("输入内容不能为空",2007),
    READ_NOTIFICATION_FAIL("无法读取别人通知",2008),
    NOTIFICATION_NOT_FOUND("通知不存在或找不到",2009),

    ;

    public String getMessage(){
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    private final String message;
    private  Integer code;
     CustomizeErrorCode(String message, Integer code){
        this.message = message;
        this.code = code;
    }
}
