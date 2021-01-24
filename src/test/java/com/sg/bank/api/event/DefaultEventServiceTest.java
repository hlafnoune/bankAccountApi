package com.sg.bank.api.event;

import com.sg.bank.api.event.core.EventRepository;
import com.sg.bank.api.event.model.Event;
import com.sg.bank.api.event.model.Operation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultEventServiceTest {

    private static final String ACCOUNT_NUMBER = "SG00000001";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100);
    private static final BigDecimal BALANCE = BigDecimal.valueOf(5000);

    @InjectMocks
    private DefaultEventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Nested
    class traceDepositOperation {

        @Test
        void should_trace_a_deposit_operation() {

            ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);

            eventService.traceDepositOperation(ACCOUNT_NUMBER, AMOUNT, BALANCE);

            verify(eventRepository).trace(eventArgumentCaptor.capture());

            final String description = String.format("Deposit of %.2f to the balance %.2f", AMOUNT, BALANCE);
            Event actualEvent = eventArgumentCaptor.getValue();
            Event expectedEvent = buildEvent(ACCOUNT_NUMBER, description, Operation.DEPOSIT);
            assertThat(actualEvent).usingRecursiveComparison().isEqualTo(expectedEvent);
        }

        private Event buildEvent(String accountNumber, String description, Operation operation) {
            return Event.Builder.getInstance()
                    .withAccountNumber(accountNumber)
                    .withOperation(operation)
                    .withDescription(description)
                    .build();
        }
    }

}
