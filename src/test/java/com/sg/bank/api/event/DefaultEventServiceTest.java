package com.sg.bank.api.event;

import com.sg.bank.api.event.core.EventRepository;
import com.sg.bank.api.event.dto.EventDto;
import com.sg.bank.api.event.model.Event;
import com.sg.bank.api.event.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
            Event expectedEvent = buildEvent(description, Operation.DEPOSIT);
            assertThat(actualEvent).usingRecursiveComparison().isEqualTo(expectedEvent);
        }
    }

    @Nested
    class traceWithdrawalOperation {

        @Test
        void should_trace_a_withdrawal_operation() {

            ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);

            eventService.traceWithdrawalOperation(ACCOUNT_NUMBER, AMOUNT, BALANCE);

            verify(eventRepository).trace(eventArgumentCaptor.capture());

            final String description = String.format("Withdrawal of %.2f from the balance %.2f", AMOUNT, BALANCE);
            Event actualEvent = eventArgumentCaptor.getValue();
            Event expectedEvent = buildEvent(description, Operation.WITHDRAWAL);
            assertThat(actualEvent).usingRecursiveComparison().isEqualTo(expectedEvent);
        }
    }

    @Nested
    class findEventsByAccountNumber {

        @BeforeEach
        void setUp() {
            List<Event> events = List.of(
                    buildEvent("Deposit of 20.00 to the balance 5000.00", Operation.DEPOSIT),
                    buildEvent("Deposit of 30.00 to the balance 5050.00", Operation.DEPOSIT),
                    buildEvent("Withdrawal of 45.00 from the balance 5005.00", Operation.WITHDRAWAL)
            );
            when(eventRepository.findEventsByAccountNumber(ACCOUNT_NUMBER)).thenReturn(events);
        }

        @Test
        void should_find_all_event_by_accountNumber() {
            eventService.findEventsByAccountNumber(ACCOUNT_NUMBER);
            verify(eventRepository).findEventsByAccountNumber(ACCOUNT_NUMBER);
        }

        @Test
        void should_transform_events_to_eventsDto_and_return_them() {
            List<EventDto> actualEvents = eventService.findEventsByAccountNumber(ACCOUNT_NUMBER);

            List<EventDto> expectedEvents = List.of(
                    buildEventDto("Deposit of 20.00 to the balance 5000.00", Operation.DEPOSIT),
                    buildEventDto("Deposit of 30.00 to the balance 5050.00", Operation.DEPOSIT),
                    buildEventDto("Withdrawal of 45.00 from the balance 5005.00", Operation.WITHDRAWAL)
            );
            assertThat(actualEvents).usingFieldByFieldElementComparator().containsAll(expectedEvents);
        }
    }

    private Event buildEvent(String description, Operation operation) {
        return Event.Builder.getInstance()
                .withAccountNumber(ACCOUNT_NUMBER)
                .withOperation(operation)
                .withDescription(description)
                .build();
    }

    private EventDto buildEventDto(String description, Operation operation) {
        return EventDto.Builder.getInstance()
                .withOperation(operation.name())
                .withDescription(description)
                .build();
    }

}
