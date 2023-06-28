package com.gmf.user_management.core.errors;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus httpStatus;
    private String message;
    private String debugMessage;
    private List<AppSubError> errorsDetails;

    private AppError() {
        this.timestamp = LocalDateTime.now();
    }

    public AppError(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
    }

    public AppError(HttpStatus httpStatus, String message) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public AppError(HttpStatus httpStatus, String message, Throwable debugMessage) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = debugMessage.getLocalizedMessage();
    }

    public AppError(HttpStatus httpStatus, Throwable debugMessage) {
        this();
        this.httpStatus = httpStatus;
        this.message = "Unexpected error";
        this.debugMessage = debugMessage.getLocalizedMessage();
    }

    public void addSubError(AppSubError error) {
        if(errorsDetails == null) {
            errorsDetails = new ArrayList<AppSubError>();
        }
        errorsDetails.add(error);
    }

    public void addAllSubError(List<AppSubError> errors) {
        if(errorsDetails == null) {
            errorsDetails = new ArrayList<AppSubError>();
        }
        errorsDetails.addAll(errors);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new AppParameterValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new AppParameterValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getField(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

}
