package com.gmf.user_management.core.exceptions;


import com.gmf.user_management.core.errors.AppError;
import com.gmf.user_management.core.errors.NoHandlerError;
import com.gmf.user_management.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity handleNotFoundException(NotFoundException exception) {
        AppError appError = new AppError(exception.getHttpStatus(), exception.getMessage());
        if (exception.getDetails().size() != 0) {
            appError.addAllSubError(exception.getDetails());
        } else {
            appError.addAllSubError(new ArrayList<>());
        }

        return new ResponseEntity(appError, exception.getHttpStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParamsRequiredException.class)
    protected ResponseEntity handleParamsRequiredException(ParamsRequiredException exception) {
        AppError appError = new AppError(exception.getHttpStatus(), exception.getMessage());
        if (exception.getDetails().size() != 0) {
            appError.addAllSubError(exception.getDetails());
        } else {
            appError.addAllSubError(new ArrayList<>());
        }

        return new ResponseEntity(appError, exception.getHttpStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoContentException.class)
    protected ResponseEntity handleNoContentException(NoContentException exception) {
        AppError appError = new AppError(exception.getHttpStatus(), exception.getMessage());
        if (exception.getDetails().size() != 0) {
            appError.addAllSubError(exception.getDetails());
        } else {
            appError.addAllSubError(new ArrayList<>());
        }

        return new ResponseEntity(appError, exception.getHttpStatus());
    }

    @ExceptionHandler(AppException.class)
    protected void handleAppException(AppException ex, HttpServletResponse response) {
        handleAppException(ex);
    }

    private AppException handleAppException(Exception exception) {
        if (exception instanceof AppException) {
            return (AppException) exception;
        } else if (exception instanceof IllegalArgumentException) {
            return new AppException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new AppException(exception.getMessage(), exception, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleException(Exception exception, HttpServletResponse response) {
        if (exception instanceof IOException) {
            log.error("IOEXception caused here");
            AppError appError = new AppError(HttpStatus.NO_CONTENT, exception.getMessage());
            return new ResponseEntity(appError, appError.getHttpStatus());
        }

        if (exception instanceof NoContentException) {
            log.error("No Content Exception caused here");
            AppError appError = new AppError(HttpStatus.NO_CONTENT, exception.getMessage());
            return new ResponseEntity(appError, appError.getHttpStatus());
        }

        if (exception instanceof NotFoundException) {
            log.error("Notfound Exception caused here");
            AppError appError = new AppError(HttpStatus.NOT_FOUND, exception.getMessage());
            return new ResponseEntity(appError, appError.getHttpStatus());
        }

        if (exception instanceof IllegalArgumentException || exception instanceof HttpException) {

            Throwable cause = exception.getCause();

            if(cause instanceof NotFoundException) {
                log.error("Data Not Found");
                AppError appError = new AppError(HttpStatus.NOT_FOUND, exception.getMessage());
                return new ResponseEntity(appError, appError.getHttpStatus());
            } else {

                log.error("Something went wrong");
                AppError appError = new AppError(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
                return new ResponseEntity(appError, appError.getHttpStatus());
            }
        }

        if (exception instanceof ExecutionException) {
            Throwable cause = exception.getCause();

            if (cause instanceof NotFoundException) {
                log.error("Notfound Exception caused here");
                AppError appError = new AppError(HttpStatus.NOT_FOUND, exception.getMessage());
                return new ResponseEntity(appError, appError.getHttpStatus());
            }

            if (cause instanceof NoContentException) {
                log.error("No Content Exception caused here of ExecutionException");
                AppError appError = new AppError(HttpStatus.NO_CONTENT, exception.getMessage());
                return new ResponseEntity(appError, appError.getHttpStatus());
            }
        }

        if(exception instanceof DataIntegrityViolationException) {
            String errorMessage = "Conflict on Column: " + StringUtil.transformToCamelCase(extractConflictingValue((DataIntegrityViolationException) exception));
            log.error(errorMessage);
            AppError appError = new AppError(HttpStatus.CONFLICT, errorMessage);
            return new ResponseEntity(appError, appError.getHttpStatus());
        }

        if(exception instanceof BadRequestException) {
            String errorMessage = "Bad Request: " + exception.getMessage();
            log.error(errorMessage);
            AppError appError = new AppError(HttpStatus.CONFLICT, errorMessage);
            return new ResponseEntity(appError, appError.getHttpStatus());
        }

        if(exception instanceof ProcessingException) {
            Throwable cause = exception.getCause();
            if (cause instanceof BadRequestException) {
                log.error("ProcessingException => {} {}", exception.getMessage(), exception.getStackTrace());
                AppError appError = new AppError(HttpStatus.BAD_REQUEST, exception.getMessage());
                return new ResponseEntity(appError, appError.getHttpStatus());
            }

        }
        log.error("GENERAL Exception caused here: {}", exception.toString());
        AppError appError = new AppError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity(appError, appError.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        AppError appError = new AppError(status, ex.getMessage());
        NoHandlerError noHandlerError = new NoHandlerError(ex.getHttpMethod(),
                String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        appError.addSubError(noHandlerError);

        return new ResponseEntity(appError, status);
    }

    /**
     * Handle when application can't give proper response, such as JSON Malformed
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        AppError appError = new AppError(status, "parameters was missing");
        return new ResponseEntity(appError, status);
    }

    /**
     * Handle when required parameter is missing
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        AppError appError = new AppError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity(appError, status);
    }

    /**
     * Handle @Validated annotations
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity handleConstraintValidation(ConstraintViolationException exception) {
        AppError appError = new AppError(HttpStatus.BAD_REQUEST, "validation has been failed");
        appError.addValidationErrors(exception.getConstraintViolations());
        return new ResponseEntity(appError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle @Validate annotations
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        AppError appError = new AppError(status, "validation failed");
        appError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        return new ResponseEntity(appError, status);
    }

    /**
     * Handle miss match data type
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity handleMethodArgumentMissmatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error(request.getParameterMap().toString());
        String _message = String.format("Parameter '%s' with value '%s', should be type of %s", ex.getName(),
                ex.getValue(), ex.getRequiredType().getSimpleName());
        AppError appError = new AppError(HttpStatus.BAD_REQUEST, _message);
        return new ResponseEntity(appError, HttpStatus.BAD_REQUEST);
    }

    private String extractConflictingValue(DataIntegrityViolationException ex) {
        // Extract the relevant information from the exception
        // You might need to inspect the exception message or use a specific exception subclass for more details
        // This depends on the specific database driver and the constraint that caused the violation
        // Example: extracting the violating value from a unique constraint violation
        String message = ex.getMostSpecificCause().getMessage();
        int startIndex = message.indexOf("Detail: Key (") + "Detail: Key (".length();
        int endIndex = message.indexOf(")=(", startIndex);
        return message.substring(startIndex, endIndex);
    }
}
