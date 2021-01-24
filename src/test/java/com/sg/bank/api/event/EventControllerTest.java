package com.sg.bank.api.event;

import com.sg.bank.api.event.core.EventService;
import com.sg.bank.api.event.dto.EventDto;
import com.sg.bank.api.event.model.Operation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    private static final String EVENTS_URL = "/api/v1/events/";
    private static final String ACCOUNT_NUMBER = "SG00000001";
    private final String GET_ALL_EVENTS_URL = EVENTS_URL + ACCOUNT_NUMBER;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Nested
    class findAllEventsFor {

        private List<EventDto> events = List.of(
                buildEventDto("Deposit of 20.00 to the balance 5000.00", Operation.DEPOSIT),
                buildEventDto("Deposit of 30.00 to the balance 5050.00", Operation.DEPOSIT),
                buildEventDto("Withdrawal of 45.00 from the balance 5005.00", Operation.WITHDRAWAL)
        );

        @Test
        void should_call_event_service_to_find_all_events_for_the_account_and_return_OK_status() throws Exception {

            given(eventService.findEventsByAccountNumber(ACCOUNT_NUMBER)).willReturn(events);

            mockMvc.perform(get(GET_ALL_EVENTS_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.events.size()", is(events.size())))
                    .andReturn();

            verify(eventService).findEventsByAccountNumber(ACCOUNT_NUMBER);
        }

    }

    private EventDto buildEventDto(String description, Operation operation) {
        return EventDto.Builder.getInstance()
                .withOperation(operation.name())
                .withDescription(description)
                .build();
    }
}
