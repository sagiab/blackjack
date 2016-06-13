package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.exception.*;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;
import com.spalah.courses.projects.blackjack.model.domain.gameover.GameOver;
import com.spalah.courses.projects.blackjack.model.domain.response.BetCreateResponse;
import com.spalah.courses.projects.blackjack.model.domain.response.TableGameStep;
import com.spalah.courses.projects.blackjack.model.domain.status.StatusMessage;
import com.spalah.courses.projects.blackjack.service.TableGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
@Controller
public class TableGameController {
    @Autowired
    TableGameService tableGameService;

    @RequestMapping(
            value = "/account/{login}/table/{tableId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public List<Command> getAvailCommands(
            @PathVariable String login,
            @PathVariable Long tableId
    ) throws AccountException, TableException {
        return tableGameService.getAvailCommands(login, tableId);
    }

    @RequestMapping(
            value = "/account/{login}/table/{tableId}/bet/{betSize}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public BetCreateResponse createBet(
            @PathVariable String login,
            @PathVariable int betSize,
            @PathVariable Long tableId
    ) throws BetException, AllCardsWereUsedException, AccountException, TableException, GameOverException {
        tableGameService.createBet(login, betSize, tableId);
        TableGameStep step = tableGameService.startFirstRound(login, tableId);
        StatusMessage status =
                new StatusMessage().well("Bet on table #" + tableId + " created, size = " + betSize);
        return new BetCreateResponse(status, step);
    }

    @RequestMapping(
            value = "/account/{login}/table/{tableId}/bet/delete",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Bet deleteBet(
            @PathVariable String login,
            @PathVariable Long tableId
    ) throws AccountException, TableException {
        return tableGameService.deleteBet(login, tableId);
//        return new StatusMessage().well("Bet on table #" + tableId + " deleted");
    }

    @RequestMapping(
            value = "/account/{login}/table/{tableId}/hit",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public TableGameStep hit(
            @PathVariable String login,
            @PathVariable Long tableId
    ) throws BetException, AllCardsWereUsedException, AccountException, TableException, GameOverException {
        TableGameStep step = tableGameService.hit(login, tableId);
        return step;
    }

    @RequestMapping(
            value = "/account/{login}/table/{tableId}/stand",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public GameOver stand(
            @PathVariable String login,
            @PathVariable Long tableId
    ) throws BetException, AllCardsWereUsedException, AccountException, TableException, GameOverException {
        GameOver gameOver = tableGameService.stand(login, tableId);
        return gameOver;
    }
}
