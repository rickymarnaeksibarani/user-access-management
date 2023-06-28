package com.gmf.user_management.core.exceptions;

import com.gmf.user_management.core.errors.AppSubError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NotFoundException extends AppException {

    private List<AppSubError> details = new ArrayList<>();

    public NotFoundException() {
        super("Resource not found", HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, List<AppSubError> details){
        super(message, HttpStatus.NOT_FOUND);
        this.details = details;
    }

    public NotFoundException(String message, AppSubError details){
        super(message, HttpStatus.NOT_FOUND);
        this.details.add(details);
    }
}
