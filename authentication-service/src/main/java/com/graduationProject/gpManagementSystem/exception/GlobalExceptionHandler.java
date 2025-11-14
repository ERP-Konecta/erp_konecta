package com.graduationProject.gpManagementSystem.exception;

import java.time.LocalDateTime;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.AuthenticationException;
 import org.springframework.validation.FieldError;
 import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.graduationProject.gpManagementSystem.dto.ErrorResponse;

// import jakarta.validation.ConstraintViolation;
// import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> userAlreadyExistExceptionHandling(UserAlreadyExistException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundExceptionHandling(ResourceNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }














@ExceptionHandler(InvalidPasswordException.class)
public ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException ex, WebRequest request) {
    ErrorResponse errorDetails = new ErrorResponse(
        LocalDateTime.now(),
        ex.getMessage(),
        request.getDescription(false),
        HttpStatus.BAD_REQUEST
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
}





    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now()
                , exception.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }





    ///////////////////////////////////////////////////////////////////////////
    /// 
    /// 
    /// 
    /// 
    /// 
    /// 
    /// 
    /// 
    /// 
    /// 
    /// 
    /// 
    ///
    /// 
    ///  







     @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandling(MethodArgumentNotValidException exception) {

        ValidationFailedResponse error = ValidationFailedResponse
                .builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timeStamp(LocalDateTime.now())
                .build();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {

            error.getViolations().add(new ViolationErrors(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()));
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    
//     @ExceptionHandler(ConstraintViolationException.class)
//     ResponseEntity<Object> onConstraintValidationException(ConstraintViolationException e) {

//         ValidationFailedResponse error = ValidationFailedResponse
//                 .builder()
//                 .httpStatus(HttpStatus.BAD_REQUEST)
//                 .timeStamp(LocalDateTime.now())
//                 .build();

//         for (ConstraintViolation<?> violation : e.getConstraintViolations()) {

//             error.getViolations().add(ViolationErrors
//                     .builder()
//                     .fieldName(violation.getPropertyPath().toString())
//                     .message(violation.getMessage())
//                     .build());
//         }

//         return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//     }

    // @ExceptionHandler(RuntimeException.class)
    // public ResponseEntity<Object> runtimeExceptionHandling(RuntimeException exception, WebRequest request) {
    //     return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), exception.getMessage(),
    //             request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    // }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request) {
    //     return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now()
    //             , exception.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR),
    //             HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    // @ExceptionHandler(AuthenticationException.class)
    // public ResponseEntity<Object> authenticationExceptionHandling(AuthenticationException exception, WebRequest request) {
    //     return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now()
    //             , String.format("Incorrect email or password credentials provided. [ %s ]", exception.getMessage()),
    //             request.getDescription(false), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    // }


}