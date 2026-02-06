package com.air.pujanke.exception.handler;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public String handleValidationFailure(RedirectAttributes ra, HttpServletRequest request) {
        ra.addFlashAttribute("error", "Invalid input.");
        return "redirect:" + request.getRequestURI();
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public String handleInvalidArgumentException(InvalidArgumentException ex, RedirectAttributes ra, HttpServletRequest request) {
        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:" + request.getRequestURI();
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleOtherExceptions(RedirectAttributes ra, HttpServletRequest request) {
        ra.addFlashAttribute("error", "An unexpected error occurred.");
        return "redirect:" + request.getRequestURI();
    }

}
