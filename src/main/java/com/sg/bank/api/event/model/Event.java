package com.sg.bank.api.event.model;

import java.time.LocalDateTime;

public class Event {

    private String accountNumber;
    private Operation operation;
    private String description;
    private LocalDateTime createDate;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public static class Builder {

        private String accountNumber;
        private Operation operation;
        private String description;
        private LocalDateTime createDate;

        private Builder() {
        }

        public static Builder getInstance() {
            return new Builder();
        }

        public Builder withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder withOperation(Operation operation) {
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

        public Event build() {
            Event event = new Event();
            event.accountNumber = accountNumber;
            event.operation = operation;
            event.description = description;
            event.createDate = createDate;
            return event;
        }
    }
}
