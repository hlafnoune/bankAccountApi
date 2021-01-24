package com.sg.bank.api.event.core;

import com.sg.bank.api.event.model.Event;

public interface EventRepository {

    void trace(Event event);
}
