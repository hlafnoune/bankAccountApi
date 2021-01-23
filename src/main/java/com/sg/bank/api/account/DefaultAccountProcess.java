package com.sg.bank.api.account;

import com.sg.bank.api.account.core.AccountProcess;
import com.sg.bank.api.account.core.AccountService;
import com.sg.bank.api.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultAccountProcess implements AccountProcess {

    private final AccountService accountService;

    @Autowired
    public DefaultAccountProcess(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = accountService.findAccountByAccountNumber(accountNumber);
        accountService.deposit(account, amount);
    }
}
