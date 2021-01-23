package com.sg.bank.api.account.core;

import com.sg.bank.api.account.model.Account;

import java.math.BigDecimal;

public interface AccountRepository {

    Account findAccountByAccountNumber(String accountNumber);

    void updateBalanceByAccountNumber(String accountNumber, BigDecimal newBalance);

}
