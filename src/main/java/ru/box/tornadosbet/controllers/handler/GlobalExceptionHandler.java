package ru.box.tornadosbet.controllers.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.box.tornadosbet.exceptions.BoxerException;
import ru.box.tornadosbet.exceptions.ResultException;
import ru.box.tornadosbet.exceptions.UserNotFoundException;
import ru.box.tornadosbet.exceptions.RegistrationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<String> handleException(RegistrationException e) {
        String str = e.getMessage();
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BoxerException.class)
    public ResponseEntity<String> handleBoxerException(BoxerException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResultException.class)
    public ResponseEntity<String> handleResultException(ResultException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);

    }
}
