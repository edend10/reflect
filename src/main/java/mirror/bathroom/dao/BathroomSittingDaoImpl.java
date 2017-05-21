package mirror.bathroom.dao;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.Map;

public class BathroomSittingDaoImpl implements BathroomSittingDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BathroomSittingDaoImpl.class);

    private static final String INSERT_SITTING_QUERY =
            "INSERT INTO bathroom.sitting (start_time, end_time) VALUES (:startTime, :endTime)";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public BathroomSittingDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertBathroomSitting(BathroomSitting sitting) {
        String query = INSERT_SITTING_QUERY;
        Map<String, String> params = ImmutableMap.of(
                "startTime", sitting.getStartTime().toString(),
                "endTime", sitting.getEndTime().toString()
        );

        try {
            jdbcTemplate.update(query, params);
        } catch (DataAccessException e) {
            LOGGER.error(String.format("Error executing query: %s with params: %s", query, params), e);
        }
    }
}
