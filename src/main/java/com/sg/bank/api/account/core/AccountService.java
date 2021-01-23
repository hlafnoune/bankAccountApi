package com.sg.bank.api.account.core;

import com.sg.bank.api.account.model.Account;

import java.math.BigDecimal;

public interface AccountService {

    Account findAccountByAccountNumber(String accountNumber);

    void deposit(Account account, BigDecimal amount);

}
