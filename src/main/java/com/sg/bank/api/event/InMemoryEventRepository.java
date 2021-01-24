package com.sg.bank.api.event;

import com.sg.bank.api.AbstractRepository;
import com.sg.bank.api.event.core.EventRepository;
import com.sg.bank.api.event.model.Event;
import com.sg.bank.api.event.model.Operation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryEventRepository extends AbstractRepository implements EventRepository {

    private static final String INSERT_NEW_EVENT = "INSERT INTO EVENTS (ACCOUNT_NUMBER, OPERATION, DESCRIPTION , CREATE_DATE) " +
            "VALUES (:accountNumber, :operation, :description, SYSDATE)";

    private static final String FIND_EVENTS_BY_ACCOUNT_NUMBER = "SELECT * FROM EVENTS WHERE ACCOUNT_NUMBER =:accountNumber";


    @Override
    public void trace(Event event) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountNumber", event.getAccountNumber());
        parameters.addValue("operation", event.getOperation().name());
        parameters.addValue("description", event.getDescription());

        getNamedParameterJdbcTemplate().update(INSERT_NEW_EVENT, parameters);
    }

    @Override
    public List<Event> findEventsByAccountNumber(String accountNumber) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountNumber", accountNumber);
        try {
            return getNamedParameterJdbcTemplate().query(FIND_EVENTS_BY_ACCOUNT_NUMBER, parameters, getEventsRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private RowMapper<Event> getEventsRowMapper() {
        return (resultSet, i) -> Event.Builder.getInstance()
                .withAccountNumber(resultSet.getString("ACCOUNT_NUMBER"))
                .withOperation(Operation.valueOf(resultSet.getString("OPERATION")))
                .withDescription(resultSet.getString("DESCRIPTION"))
                .withCreateDate(resultSet.getTimestamp("CREATE_DATE").toLocalDateTime())
                .build();
    }
}
