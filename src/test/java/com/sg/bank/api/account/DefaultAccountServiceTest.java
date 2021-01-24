package com.sg.bank.api.account;

import com.sg.bank.api.account.core.AccountRepository;
import com.sg.bank.api.account.model.Account;
import com.sg.bank.api.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultAccountServiceTest {

    private static final String ACCOUNT_NUMBER = "SG00000001";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100);

    @InjectMocks
    private DefaultAccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(ACCOUNT_NUMBER, BigDecimal.valueOf(5000));
    }


    @Nested
    class findAccountByAccountNumber {

        @BeforeEach
        void setUp() {
            when(accountRepository.findAccountByAccountNumber(ACCOUNT_NUMBER)).thenReturn(account);
        }

        @Test
        void should_call_the_repository_to_find_account_by_accounNumber() {
            accountService.findAccountByAccountNumber(ACCOUNT_NUMBER);
            verify(accountRepository).findAccountByAccountNumber(ACCOUNT_NUMBER);
        }

        @Test
        void should_throw_AccountNotFoundException_if_the_repository_return_null() {

            when(accountRepository.findAccountByAccountNumber(ACCOUNT_NUMBER)).thenReturn(null);

            Exception exception = assertThrows(AccountNotFoundException.class, () -> {
                accountService.findAccountByAccountNumber(ACCOUNT_NUMBER);
            });

            assertThat(exception.getMessage()).isEqualTo("Account SG00000001 not found");
        }
    }

    @Nested
    class Deposit {

        @Test
        void should_set_the_amount_as_new_balance_if_the_balance_is_null() {
            account.setBalance(null);
            accountService.deposit(account, AMOUNT);
            verify(accountRepository).updateBalanceByAccountNumber(ACCOUNT_NUMBER, BigDecimal.valueOf(100));
        }

        @Test
        void should_update_account_balance_by_accountNumber() {
            accountService.deposit(account, AMOUNT);
            verify(accountRepository).updateBalanceByAccountNumber(ACCOUNT_NUMBER, BigDecimal.valueOf(5100));
        }
    }

    @Nested
    class Withdrawal {

        @Test
        void should_set_the_amount_as_new_balance_if_the_balance_is_null() {
            account.setBalance(null);
            accountService.withdrawal(account, AMOUNT);
            verify(accountRepository).updateBalanceByAccountNumber(ACCOUNT_NUMBER, BigDecimal.valueOf(-100));
        }

        @Test
        void should_update_account_balance_by_accountNumber() {
            accountService.withdrawal(account, AMOUNT);
            verify(accountRepository).updateBalanceByAccountNumber(ACCOUNT_NUMBER, BigDecimal.valueOf(4900));
        }
    }


}
