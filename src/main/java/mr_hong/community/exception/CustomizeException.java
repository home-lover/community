package mr_hong.community.exception;

public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(InterfaceErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
