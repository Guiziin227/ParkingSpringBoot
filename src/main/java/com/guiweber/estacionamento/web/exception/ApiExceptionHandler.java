package com.guiweber.estacionamento.web.exception;

import com.guiweber.estacionamento.exception.CodUniqueViolationException;
import com.guiweber.estacionamento.exception.CpfUniqueViolationException;
import com.guiweber.estacionamento.exception.UserNotFoundException;
import com.guiweber.estacionamento.exception.UsernameUniqueViolationException;
import com.guiweber.estacionamento.exception.EditPasswordException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException e,
                                                              HttpServletRequest request) {
        log.error("EditPasswordException: ", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(
                request,
                HttpStatus.FORBIDDEN,
                e.getMessage()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException m,
                                                                        HttpServletRequest request,
                                                                        BindingResult bindingResult) {

        log.error("MethodArgumentNotValidException: ", m, bindingResult);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(
                request,
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Campo(s) inválido(s)",
                bindingResult
        ));
    }

    @ExceptionHandler({UsernameUniqueViolationException.class, CpfUniqueViolationException.class,
            CodUniqueViolationException.class})
    public ResponseEntity<ErrorMessage> usernameUniqueViolationException(RuntimeException e,
                                                                         HttpServletRequest request) {
        log.error("UsernameUniqueViolationException: ", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(
                request,
                HttpStatus.CONFLICT,
                e.getMessage()
        ));
    }

    @ExceptionHandler(EditPasswordException.class)
    public ResponseEntity<ErrorMessage> editPasswordException(EditPasswordException e,
                                                              HttpServletRequest request) {
        log.error("EditPasswordException: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(
                request,
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException e,
                                                              HttpServletRequest request) {
        log.error("UserNotFoundException: ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(
                request,
                HttpStatus.NOT_FOUND,
                e.getMessage()
        ));
    }
}
