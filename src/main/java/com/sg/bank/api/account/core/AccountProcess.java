package com.sg.bank.api.account.core;

import java.math.BigDecimal;

public interface AccountProcess {

    void deposit(String accountNumber, BigDecimal amount);

}
