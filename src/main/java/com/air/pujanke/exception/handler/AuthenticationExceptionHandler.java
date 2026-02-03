package com.air.pujanke.exception.handler;

import com.air.pujanke.controller.AuthenticationController;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(assignableTypes = {AuthenticationController.class})
public class AuthenticationExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ModelAndView handleAuthenticationException(BindException ex) {
        var mav = new ModelAndView();
        return mav;
    }
}
