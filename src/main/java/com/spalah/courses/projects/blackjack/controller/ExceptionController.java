package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.exception.AllCardsWereUsedException;
import com.spalah.courses.projects.blackjack.exception.GameOverException;
import com.spalah.courses.projects.blackjack.exception.TableException;
import com.spalah.courses.projects.blackjack.model.domain.gameover.GameOver;
import com.spalah.courses.projects.blackjack.model.domain.status.StatusMessage;
import com.spalah.courses.projects.blackjack.service.TableGameService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TableGameService tableGameService;

    @ExceptionHandler(AccountException.class)
    @ResponseBody
    public StatusMessage accountException(AccountException e) {
        return new StatusMessage().error(e);
    }

    @ExceptionHandler(TableException.class)
    @ResponseBody
    public StatusMessage tableException(TableException e) {
        return new StatusMessage().error(e);
    }

    @ExceptionHandler(GameOverException.class)
    @ResponseBody
    public GameOver gameOverException(GameOverException e)
            throws AllCardsWereUsedException, TableException, AccountException {
        GameOver gameOver = tableGameService.stand(e.getLogin(), e.getTableId());
        return gameOver;
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
        e.printStackTrace();
        return new StatusMessage().error(e);
    }
}
