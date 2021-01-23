package com.sg.bank.api.account;

import com.sg.bank.api.account.core.AccountProcess;
import com.sg.bank.api.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountProcess accountProcess;

    @Nested
    class Deposit {

        private static final String ACCOUNT_NUMBER = "SG00000001";
        private final BigDecimal AMOUNT = BigDecimal.valueOf(100);
        private final String ACCOUNT_DEPOSIT_URL = "/api/v1/accounts/" + ACCOUNT_NUMBER + "/deposit/" + AMOUNT;

        @Test
        void should_call_account_process_to_depose_money_and_return_OK_status() throws Exception {

            doNothing().when(accountProcess).deposit(anyString(), any(BigDecimal.class));

            mockMvc.perform(get(ACCOUNT_DEPOSIT_URL))
                    .andExpect(status().isOk());

            verify(accountProcess).deposit(ACCOUNT_NUMBER, AMOUNT);
        }

        @Test
        void should_call_return_not_found_status_if_the_process_throw_AccountNotFoundException() throws Exception {

            doThrow(new AccountNotFoundException(ACCOUNT_NUMBER)).when(accountProcess).deposit(anyString(), any(BigDecimal.class));

            mockMvc.perform(get(ACCOUNT_DEPOSIT_URL))
                    .andExpect(status().isNotFound());

            verify(accountProcess).deposit(ACCOUNT_NUMBER, AMOUNT);
        }

        @Test
        void should_call_return_internal_server_error_status_if_the_process_throw_any_other_exception() throws Exception {

            doThrow(new RuntimeException()).when(accountProcess).deposit(anyString(), any(BigDecimal.class));

            mockMvc.perform(get(ACCOUNT_DEPOSIT_URL))
                    .andExpect(status().isInternalServerError());

            verify(accountProcess).deposit(ACCOUNT_NUMBER, AMOUNT);
        }


    }
}