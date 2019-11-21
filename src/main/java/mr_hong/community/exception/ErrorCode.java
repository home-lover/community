package mr_hong.community.exception;

public enum ErrorCode implements InterfaceErrorCode {
    QUESTION_NOT_FOUND("你找的问题不在了！");
    private String message;
    @Override
    public String getMessage() {
        return message;
    }

    ErrorCode(String message) {
        this.message = message;
    }
}
