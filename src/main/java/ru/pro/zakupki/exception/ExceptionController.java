package ru.pro.zakupki.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ExistEmailException.class)
    public ErrorResponse handleEmailExist(ExistEmailException e) {
        return new ErrorResponse(e.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ErrorResponse handleInternalAuthException(InternalAuthenticationServiceException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotFoundUserException.class)
    public ErrorResponse handleNotFoundUser(NotFoundUserException e) {
        return new ErrorResponse(e.getMessage());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundProductException.class)
    public ErrorResponse handleNotFoundUser(NotFoundProductException e) {
        return new ErrorResponse(e.getMessage());
    }
}
