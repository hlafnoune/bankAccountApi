package com.sg.bank.api.exceptions;

public class AccountNotFoundException extends RuntimeException {

    private static final String message = "Account %s not found";

    public AccountNotFoundException(String accountNumber) {
        super(String.format(message, accountNumber));
    }
}
