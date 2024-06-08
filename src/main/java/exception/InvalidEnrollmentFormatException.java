package exception;

public class InvalidEnrollmentFormatException extends RuntimeException {
    public InvalidEnrollmentFormatException() {
        super();
    }

    public InvalidEnrollmentFormatException(String message) {
        super(message);
    }

    public InvalidEnrollmentFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEnrollmentFormatException(Throwable cause) {
        super(cause);
    }
}
