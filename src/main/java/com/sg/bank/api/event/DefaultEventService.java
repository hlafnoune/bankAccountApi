package com.sg.bank.api.event;

import com.sg.bank.api.event.core.EventRepository;
import com.sg.bank.api.event.core.EventService;
import com.sg.bank.api.event.model.Event;
import com.sg.bank.api.event.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultEventService implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public DefaultEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void traceDepositOperation(final String accountNumber, final BigDecimal amount, final BigDecimal balance) {
        final String description = String.format("Deposit of %.2f to the balance %.2f", amount, balance);
        traceEvent(accountNumber, description, Operation.DEPOSIT);
    }

    @Override
    public void traceWithdrawalOperation(String accountNumber, BigDecimal amount, BigDecimal balance) {
        final String description = String.format("Withdrawal of %.2f from the balance %.2f", amount, balance);
        traceEvent(accountNumber, description, Operation.WITHDRAWAL);
    }

    private void traceEvent(String accountNumber, String description, Operation withdrawal) {
        Event event = buildEvent(accountNumber, description, withdrawal);
        eventRepository.trace(event);
    }
    
    private Event buildEvent(String accountNumber, String description, Operation operation) {
        return Event.Builder.getInstance()
                .withAccountNumber(accountNumber)
                .withOperation(operation)
                .withDescription(description)
                .build();
    }
}
