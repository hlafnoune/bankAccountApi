package com.sg.bank.api.event;

import com.sg.bank.api.AbstractRepository;
import com.sg.bank.api.event.core.EventRepository;
import com.sg.bank.api.event.model.Event;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEventRepository extends AbstractRepository implements EventRepository {

    private static final String INSERT_NEW_EVENT = "INSERT INTO EVENTS (ACCOUNT_NUMBER, OPERATION, DESCRIPTION , CREATE_DATE) " +
            "VALUES (:accountNumber, :operation, :description, SYSDATE)";


    @Override
    public void trace(Event event) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountNumber", event.getAccountNumber());
        parameters.addValue("operation", event.getOperation().name());
        parameters.addValue("description", event.getDescription());

        getNamedParameterJdbcTemplate().update(INSERT_NEW_EVENT, parameters);
    }
}
