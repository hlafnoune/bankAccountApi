package com.sg.bank.api.account;

import com.sg.bank.api.AbstractRepository;
import com.sg.bank.api.account.core.AccountRepository;
import com.sg.bank.api.account.model.Account;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class InMemoryAccountRepository extends AbstractRepository implements AccountRepository {

    private static final String FIND_ACCOUNT_BY_ACCOUNT_NUMBER = "SELECT * FROM ACCOUNTS WHERE ACCOUNT_NUMBER =:accountNumber";
    private static final String UPDTAE_BALANCE_BY_ACCOUNT_NUMBER = "UPDATE ACCOUNTS SET BALANCE =:newBalance WHERE ACCOUNT_NUMBER =:accountNumber";

    @Override
    public Account findAccountByAccountNumber(String accountNumber) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountNumber", accountNumber);
        try {
            return getNamedParameterJdbcTemplate().queryForObject(FIND_ACCOUNT_BY_ACCOUNT_NUMBER, parameters, getAccountRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private RowMapper<Account> getAccountRowMapper() {
        return (resultSet, i) -> new Account(resultSet.getString("ACCOUNT_NUMBER"), resultSet.getBigDecimal("BALANCE"));
    }

    @Override
    public void updateBalanceByAccountNumber(String accountNumber, BigDecimal newBalance) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountNumber", accountNumber);
        parameters.addValue("newBalance", newBalance);
        getNamedParameterJdbcTemplate().update(UPDTAE_BALANCE_BY_ACCOUNT_NUMBER, parameters);

    }
}
