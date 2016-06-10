package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.model.dao.AccountDao;
import com.spalah.courses.projects.blackjack.model.domain.account.Account;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class AccountService {
    private static final Long STARTED_BALANCE = 10000L;
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account getAccount(String login, String password) throws AccountException {
        Account account;
        try {
            account = accountDao.getAccount(login);
        } catch (NoResultException e) {
            throw new AccountException("Login incorrect");
        }
        if (BCrypt.checkpw(password, account.getPassword())) return account;
        else throw new AccountException("Password incorrect");
    }

    public void createAccount(Account account) throws AccountException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Account>> valid = validator.validate(account);

        if (valid.size() == 0 && isUnique(account)) {
            String passHash = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
            account.setPassword(passHash);
            account.setBalance(STARTED_BALANCE);
            accountDao.createAccount(account);
        } else {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<Account> errorElement : valid) {
                errors.put(errorElement.getPropertyPath().toString(), errorElement.getMessageTemplate());
            }
            throw new AccountException("Fields aren't valid", errors);
        }
    }

    public void deleteAccount(String login, String password) throws AccountException {
        Account account;
        if (login != null && password != null) {
            account = getAccount(login, password);
            accountDao.deleteAccount(account.getLogin());
        } else {
            throw new AccountException("The account information is not complete");
        }
    }

    private boolean isUnique(Account account) throws AccountException {
        List<Account> accounts = accountDao.getAll();
        for (Account a : accounts) {
            if (a.getLogin().equals(account.getLogin())) throw new AccountException("This login is already busy.");
        }
        return true;
    }
}
