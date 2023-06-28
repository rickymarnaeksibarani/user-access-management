package com.gmf.user_management.core.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends Exception {

    private static final long serialVersionUID = -6525644934587822810L;

    private HttpStatus httpStatus;

    public AppException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public AppException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public AppException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public AppException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}