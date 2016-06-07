package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.exception.DaoException;
import com.spalah.courses.projects.blackjack.model.domain.Account;
import com.spalah.courses.projects.blackjack.model.domain.StatusMessage;
import com.spalah.courses.projects.blackjack.model.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
@Controller
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public StatusMessage createAccount(@RequestBody Account account) {
        try {
            accountService.createAccount(account);
            return new StatusMessage().well();
        } catch (DaoException e) {
            return new StatusMessage(e);
        }
    }
}
