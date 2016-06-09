package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import com.spalah.courses.projects.blackjack.model.domain.account.FormCreateAccount;
import com.spalah.courses.projects.blackjack.model.domain.account.FormLoginAccount;
import com.spalah.courses.projects.blackjack.model.domain.status.StatusMessage;
import com.spalah.courses.projects.blackjack.model.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    private Validator validator;

    public AccountController() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public StatusMessage createAccount(@RequestBody FormCreateAccount formAccount) throws AccountException {
        Account account = new Account();
        Set<ConstraintViolation<FormCreateAccount>> valid = validator.validate(formAccount);
        if (valid.size() == 0) {
            System.out.println(valid);

            account.setLogin(formAccount.getLogin());
            account.setNickName(formAccount.getNickName());
            account.setPassword(formAccount.getPassword());

            System.out.println(account);

            accountService.createAccount(account);
            return new StatusMessage().well("Account is created");
        } else {
            throw new AccountException(valid.toString());
        }
    }

    @RequestMapping(value = "/account/login", method = RequestMethod.POST)
    @ResponseBody
    public StatusMessage createAccount(@RequestBody FormLoginAccount loginAccount) throws AccountException {
            String login = loginAccount.getLogin();
            String password = loginAccount.getPassword();
            Account account = accountService.getAccount(login, password);
            return new StatusMessage()
                    .well("Account is present")
                    .add(account);
    }
}
