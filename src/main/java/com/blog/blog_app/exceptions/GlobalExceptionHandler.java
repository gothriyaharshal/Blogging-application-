package com.blog.blog_app.exceptions;

import com.blog.blog_app.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@ControllerAdvice globally handle the particular exception in spring boot

@RestControllerAdvice
public class GlobalExceptionHandler {

    //i want to handel this type of Exception if this come in my programm like resource not found Exception
    //i pass it into @Exceptionhandle()
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        String message = exception.getMessage();
        return new ResponseEntity<>(new ApiResponse(message, false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAll(
            Exception ex) {

        ApiResponse response = new ApiResponse(ex.getMessage(), false);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //This method is used for handling validation errors that occur when we use @Valid on a request object.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> methodArgumentHandling(MethodArgumentNotValidException methodArgumentNotValidException) {
        String message = methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage();

        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
