package exception;

public class InvalidStudentFormatException extends RuntimeException {
    public InvalidStudentFormatException() {
        super();
    }

    public InvalidStudentFormatException(String message) {
        super(message);
    }

    public InvalidStudentFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStudentFormatException(Throwable cause) {
        super(cause);
    }
}
