package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.exception.AllCardsWereUsedException;
import com.spalah.courses.projects.blackjack.exception.BetOutOfTypeRange;
import com.spalah.courses.projects.blackjack.exception.TableException;
import com.spalah.courses.projects.blackjack.model.domain.bet.Bet;
import com.spalah.courses.projects.blackjack.model.domain.commands.Command;
import com.spalah.courses.projects.blackjack.model.domain.status.StatusMessage;
import com.spalah.courses.projects.blackjack.model.service.TableGameService;
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
    public StatusMessage createBet(
            @PathVariable String login,
            @PathVariable int betSize,
            @PathVariable Long tableId
    ) throws BetOutOfTypeRange, AllCardsWereUsedException, AccountException, TableException {
        tableGameService.createBet(login, betSize, tableId);
        return new StatusMessage().well("Bet on table #" + tableId + " created, size = " + betSize);
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
}
