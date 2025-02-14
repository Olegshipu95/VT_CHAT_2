package messenger.exception;

public enum ErrorCode {
    // common errors
    INVALID_REQUEST,
    SERVICE_UNAVAILABLE,

    VALIDATION_FAILED,

    NOT_FOUND,
    USER_COUNT_ERROR,
    USER_DUPLICATED,
    // user errors
    FORBIDDEN,
}