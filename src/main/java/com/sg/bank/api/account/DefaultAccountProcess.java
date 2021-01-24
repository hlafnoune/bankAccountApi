package com.sg.bank.api.account;

import com.sg.bank.api.account.core.AccountProcess;
import com.sg.bank.api.account.core.AccountService;
import com.sg.bank.api.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class DefaultAccountProcess implements AccountProcess {

    private final AccountService accountService;

    @Autowired
    public DefaultAccountProcess(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public void deposit(String accountNumber, BigDecimal amount) {
        System.out.println("amount : " + amount);
        Account account = accountService.findAccountByAccountNumber(accountNumber);
        System.out.println("account.getBalance : " + account.getBalance());
        accountService.deposit(account, amount);
    }

    @Override
    @Transactional
    public void withdrawal(String accountNumber, BigDecimal amount) {
        Account account = accountService.findAccountByAccountNumber(accountNumber);
        accountService.withdrawal(account, amount);
    }
}
