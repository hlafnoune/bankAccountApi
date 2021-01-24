package com.sg.bank.api.event.dto;

import java.time.LocalDateTime;

public class EventDto {

    public String operation;
    public String description;
    public LocalDateTime createDate;

    public static class Builder {

        private String operation;
        private String description;
        private LocalDateTime createDate;

        private Builder() {
        }

        public static Builder getInstance() {
            return new Builder();
        }

        public Builder withOperation(String operation) {
            this.operation = operation;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public EventDto build() {
            EventDto event = new EventDto();
            event.operation = operation;
            event.description = description;
            event.createDate = createDate;
            return event;
        }
    }
}
