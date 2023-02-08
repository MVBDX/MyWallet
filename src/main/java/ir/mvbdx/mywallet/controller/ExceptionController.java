package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.exception.EntityNotFoundException;
import ir.mvbdx.mywallet.exception.UsernameExistException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class ExceptionController {

    // For UI Pages
    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFoundException(EntityNotFoundException exception, Model model) {
        model.addAttribute("exception", exception);
        return "error";
    }

    @ExceptionHandler(UsernameExistException.class)
    public String usernameExistException(EntityNotFoundException exception, Model model, BindingResult result) {
        result.addError(new FieldError("email", "email", "advice"));
        return "register";
    }

}