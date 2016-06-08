package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.model.domain.status.StatusMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.NoResultException;

/**
 * @author Denis Loshkarev on 08.06.2016.
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(AccountException.class)
    @ResponseBody
    public StatusMessage accountException(AccountException e) {
        return new StatusMessage().error(e);
    }
}
