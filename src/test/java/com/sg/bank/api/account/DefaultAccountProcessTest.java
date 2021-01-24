package com.sg.bank.api.account;

import com.sg.bank.api.account.core.AccountService;
import com.sg.bank.api.account.model.Account;
import com.sg.bank.api.event.core.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultAccountProcessTest {

    private static final String ACCOUNT_NUMBER = "SG00000001";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100);
    private static final BigDecimal BALANCE = BigDecimal.valueOf(5000);

    @InjectMocks
    private DefaultAccountProcess accountProcess;

    @Mock
    private AccountService accountService;

    @Mock
    private EventService eventService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(ACCOUNT_NUMBER, BALANCE);
        when(accountService.findAccountByAccountNumber(ACCOUNT_NUMBER)).thenReturn(account);
    }

    @Nested
    class Deposit {

        @Test
        void should_find_account_by_accountNumber() {
            accountProcess.deposit(ACCOUNT_NUMBER, AMOUNT);
            verify(accountService).findAccountByAccountNumber(ACCOUNT_NUMBER);
        }

        @Test
        void should_call_the_account_service_to_deposit_the_amount_in_the_account() {
            accountProcess.deposit(ACCOUNT_NUMBER, AMOUNT);
            verify(accountService).deposit(account, AMOUNT);
        }

        @Test
        void should_call_trace_the_deposit_operation() {
            accountProcess.deposit(ACCOUNT_NUMBER, AMOUNT);
            verify(eventService).traceDepositOperation(ACCOUNT_NUMBER, AMOUNT, BALANCE);
        }
    }


    @Nested
    class Withdrawal {

        @Test
        void should_find_account_by_accountNumber() {
            accountProcess.withdrawal(ACCOUNT_NUMBER, AMOUNT);
            verify(accountService).findAccountByAccountNumber(ACCOUNT_NUMBER);
        }

        @Test
        void should_call_the_account_service_to_withdrawal_the_amount_in_the_account() {
            accountProcess.withdrawal(ACCOUNT_NUMBER, AMOUNT);
            verify(accountService).withdrawal(account, AMOUNT);
        }
    }
}
