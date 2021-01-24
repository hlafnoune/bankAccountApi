package com.sg.bank.api.event.core;

import java.math.BigDecimal;

public interface EventService {

    void traceDepositOperation(String accountNumber, BigDecimal amount, BigDecimal balance);

    void traceWithdrawalOperation(String accountNumber, BigDecimal amount, BigDecimal balance);
}
