package com.gmf.user_management.core.exceptions;

import com.gmf.user_management.core.errors.AppSubError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ParamsRequiredException extends AppException {

    private List<AppSubError> details = new ArrayList<>();

    public ParamsRequiredException() {
        super("Resource not found", HttpStatus.BAD_REQUEST);
    }

    public ParamsRequiredException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public ParamsRequiredException(String message, List<AppSubError> details){
        super(message, HttpStatus.BAD_REQUEST);
        this.details = details;
    }

    public ParamsRequiredException(String message, AppSubError details){
        super(message, HttpStatus.BAD_REQUEST);
        this.details.add(details);
    }
}
