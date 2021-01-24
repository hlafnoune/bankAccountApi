package com.sg.bank.api.event.model;

public class Event {

    private String accountNumber;
    private Operation operation;
    private String description;

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

    public static class Builder {

        private String accountNumber;
        private Operation operation;
        private String description;

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

        public Event build() {
            Event event = new Event();
            event.accountNumber = accountNumber;
            event.operation = operation;
            event.description = description;
            return event;
        }
    }
}
