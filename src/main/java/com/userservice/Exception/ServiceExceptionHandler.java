package com.userservice.Exception;

import com.userservice.Errors.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    ResponseEntity customerNotFoundHandler(Exception exception, ServletWebRequest request){
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setCode("500");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
