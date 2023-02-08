package ir.mvbdx.mywallet.controller.api;

import ir.mvbdx.mywallet.exception.UsernameExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionRestController {

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<?> UsernameExistException(UsernameExistException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}