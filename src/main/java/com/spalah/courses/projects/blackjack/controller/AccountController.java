package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.account.FormCreateAccount;
import com.spalah.courses.projects.blackjack.model.domain.account.FormLoginAccount;
import com.spalah.courses.projects.blackjack.model.domain.response.AccountInfoResponse;
import com.spalah.courses.projects.blackjack.model.domain.status.StatusMessage;
import com.spalah.courses.projects.blackjack.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    public AccountController() {
    }

    @RequestMapping(
            value = "/account",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public StatusMessage create(@Valid @RequestBody FormCreateAccount formAccount)
            throws AccountException {
        Account account = new Account();
        account.setLogin(formAccount.getLogin());
        account.setNickName(formAccount.getNickName());
        account.setPassword(formAccount.getPassword());
        accountService.createAccount(account);
        return new StatusMessage().well("Account " + account.getLogin() + " is created");
    }

    @RequestMapping(
            value = "/account/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST
    )
    @ResponseBody
    public AccountInfoResponse login(@Valid @RequestBody FormLoginAccount loginAccount)
            throws AccountException {
        String login = loginAccount.getLogin();
        String password = loginAccount.getPassword();
        Account account = accountService.getAccount(login, password);
        return new AccountInfoResponse(account);
    }

    @RequestMapping(
            value = "/account/info",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST
    )
    @ResponseBody
    public AccountInfoResponse getInfo(@Valid @RequestBody FormLoginAccount loginAccount)
            throws AccountException {
        String login = loginAccount.getLogin();
        String password = loginAccount.getPassword();
        Account account = accountService.getAccount(login, password);
        return new AccountInfoResponse(account);
    }

    @RequestMapping(
            value = "/account/{login}/{password}",
            method = RequestMethod.DELETE
    )
    @ResponseBody
    public StatusMessage delete(@PathVariable String login, @PathVariable String password)
            throws AccountException {
        accountService.deleteAccount(login, password);
        return new StatusMessage().well("Account " + login + " deleted");
    }
}
