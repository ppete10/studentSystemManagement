package exception;

public class InvalidCourseFormatException extends RuntimeException {
    public InvalidCourseFormatException() {
        super();
    }

    public InvalidCourseFormatException(String message) {
        super(message);
    }

    public InvalidCourseFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCourseFormatException(Throwable cause) {
        super(cause);
    }
}
