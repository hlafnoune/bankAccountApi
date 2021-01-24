package com.sg.bank.api.event.core;

import com.sg.bank.api.event.model.Event;

import java.util.List;

public interface EventRepository {

    void trace(Event event);

    List<Event> findEventsByAccountNumber(String accountNumber);
}
