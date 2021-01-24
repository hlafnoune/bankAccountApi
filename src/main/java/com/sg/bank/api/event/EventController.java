package com.sg.bank.api.event;

import com.sg.bank.api.event.core.EventService;
import com.sg.bank.api.event.dto.EventsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping
    @RequestMapping("/{accountNumber}")
    public ResponseEntity<EventsDto> findAllEventsFor(@PathVariable("accountNumber") String accountNumber) {

        EventsDto events = new EventsDto();
        events.events = eventService.findEventsByAccountNumber(accountNumber);

        return ResponseEntity.ok(events);
    }


}
