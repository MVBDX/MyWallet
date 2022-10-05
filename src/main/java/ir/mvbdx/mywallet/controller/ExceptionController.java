package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    // For UI Pages
    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFoundException(EntityNotFoundException exception, Model model) {
        model.addAttribute("exception", exception);
        return "error";
    }

    /*For REST APIs
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }*/

}