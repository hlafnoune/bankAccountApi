package com.sg.bank.api.account;

import com.sg.bank.api.account.core.AccountProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountProcess accountProcess;

    @Autowired
    public AccountController(AccountProcess accountProcess) {
        this.accountProcess = accountProcess;
    }


    /**
     * We hvae to be sure that the accountNumber belongs to the user connected and autorized to to that !!
     *
     * @param accountNumber
     * @param amount
     * @return
     */
    @PostMapping
    @RequestMapping("/{accountNumber}/deposit/{amount}")
    public ResponseEntity<Object> deposit(@PathVariable("accountNumber") String accountNumber,
                                          @PathVariable("amount") BigDecimal amount) {

        System.out.println(amount);
        accountProcess.deposit(accountNumber, amount);

        return ResponseEntity.ok().build();
    }

    /**
     * We hvae to be sure that the accountNumber belongs to the user connected and autorized to to that !!
     *
     * @param accountNumber
     * @param amount
     * @return
     */
    @PostMapping
    @RequestMapping("/{accountNumber}/withdrawal/{amount}")
    public ResponseEntity<Object> withdrawal(@PathVariable("accountNumber") String accountNumber,
                                             @PathVariable("amount") BigDecimal amount) {

        accountProcess.withdrawal(accountNumber, amount);

        return ResponseEntity.ok().build();
    }


}
