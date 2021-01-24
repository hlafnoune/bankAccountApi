package com.sg.bank.api.account;

import com.sg.bank.api.account.core.AccountRepository;
import com.sg.bank.api.account.core.AccountService;
import com.sg.bank.api.account.model.Account;
import com.sg.bank.api.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DefaultAccountService implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public DefaultAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }

    @Override
    public void deposit(Account account, BigDecimal amount) {
        System.out.println("account.getBalance : " + account.getBalance());
        BigDecimal newBalance = Optional.ofNullable(account.getBalance()).orElse(BigDecimal.ZERO).add(amount);
        System.out.println("account.getBalance : " + newBalance);
        accountRepository.updateBalanceByAccountNumber(account.getAccountNumber(), newBalance);
    }

    @Override
    public void withdrawal(Account account, BigDecimal amount) {
        BigDecimal newBalance = Optional.ofNullable(account.getBalance()).orElse(BigDecimal.ZERO).subtract(amount);
        accountRepository.updateBalanceByAccountNumber(account.getAccountNumber(), newBalance);
    }
}
