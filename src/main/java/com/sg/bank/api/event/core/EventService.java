package com.sg.bank.api.event.core;

import com.sg.bank.api.event.dto.EventDto;

import java.math.BigDecimal;
import java.util.List;

public interface EventService {

    void traceDepositOperation(String accountNumber, BigDecimal amount, BigDecimal balance);

    void traceWithdrawalOperation(String accountNumber, BigDecimal amount, BigDecimal balance);

    List<EventDto> findEventsByAccountNumber(String accountNumber);
}
