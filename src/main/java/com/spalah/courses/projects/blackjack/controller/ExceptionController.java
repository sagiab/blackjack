package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.model.domain.status.StatusMessage;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Denis Loshkarev on 08.06.2016.
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(AccountException.class)
    @ResponseBody
    public StatusMessage accountException(AccountException e) {
        StatusMessage statusMessage = new StatusMessage().error(e);
        statusMessage.add(e.getObject());
        return statusMessage;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public StatusMessage methodArgumentNotValidException(MethodArgumentNotValidException e) {
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        return new StatusMessage().error(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public StatusMessage exception(Exception e) {
        if (e.getMessage().contains("Unknown database")) {
//            EntityManager manager = getEntityManager();
//            Query q = manager.createNativeQuery("BEGIN " + sqlScript + " END;");
//            q.executeUpdate();
        }
        return new StatusMessage().error(e);
    }
}
