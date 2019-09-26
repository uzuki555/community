package life.wyj.community.exception;

public enum CustomizeErrorCode implements  ICustomizeErrorCode {
    QUESTION_NOT_FOUND("找不到问题，换个问题试试");


    public String getMessage(){
        return this.message;
    }
    private final String message;

    CustomizeErrorCode(String message){
        this.message = message;
    }
}
