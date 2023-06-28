package com.gmf.user_management.core.exceptions;

import com.gmf.user_management.core.errors.AppSubError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NoContentException extends AppException {

    private List<AppSubError> details = new ArrayList<>();

    public NoContentException() {
        super("Resource has no content", HttpStatus.NO_CONTENT);
    }

    public NoContentException(String message) {
        super(message, HttpStatus.NO_CONTENT);
    }

    public NoContentException(String message, List<AppSubError> details) {
        super(message, HttpStatus.NO_CONTENT);
        this.details = details;
    }

    public NoContentException(String message, AppSubError details) {
        super(message, HttpStatus.NO_CONTENT);
        this.details.add(details);
    }
}
