package com.sg.bank.api.event.transformers;

import com.sg.bank.api.event.dto.EventDto;
import com.sg.bank.api.event.model.Event;

public class EventTransformer {

    public static EventDto transform(Event event) {
        return EventDto.Builder.getInstance()
                .withOperation(event.getOperation().name())
                .withDescription(event.getDescription())
                .withCreateDate(event.getCreateDate())
                .build();
    }

}
