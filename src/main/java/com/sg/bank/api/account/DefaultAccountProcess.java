package com.sg.bank.api.account;

import com.sg.bank.api.account.core.AccountProcess;
import com.sg.bank.api.account.core.AccountService;
import com.sg.bank.api.account.model.Account;
import com.sg.bank.api.event.core.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class DefaultAccountProcess implements AccountProcess {

    private final AccountService accountService;

    private final EventService eventService;

    @Autowired
    public DefaultAccountProcess(AccountService accountService, EventService eventService) {
        this.accountService = accountService;
        this.eventService = eventService;
    }

    @Override
    @Transactional
    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = accountService.findAccountByAccountNumber(accountNumber);
        accountService.deposit(account, amount);
        eventService.traceDepositOperation(accountNumber, amount, account.getBalance());
    }

    @Override
    @Transactional
    public void withdrawal(String accountNumber, BigDecimal amount) {
        Account account = accountService.findAccountByAccountNumber(accountNumber);
        accountService.withdrawal(account, amount);
    }
}
