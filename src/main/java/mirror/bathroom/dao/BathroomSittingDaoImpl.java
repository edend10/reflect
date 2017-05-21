package mirror.bathroom.dao;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BathroomSittingDaoImpl implements BathroomSittingDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BathroomSittingDaoImpl.class);

    private static final String INSERT_SITTING_QUERY =
            "INSERT INTO bathroom.sitting (start_time, end_time) VALUES (:startTime, :endTime)";
    private static final String SELECT_SITTINGS_IN_RANGE_QUERY =
            "select distinct start_time, end_time from bathroom.sitting where start_time >= :startTime and end_time <= :endTime";



    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SittingRowMapper rowMapper;

    public BathroomSittingDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = new SittingRowMapper();
    }

    @Override
    public void insertBathroomSitting(BathroomSittingDto sitting) {
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

    @Override
    public List<BathroomSittingDto> getSittingsInRange(LocalDateTime startTime, LocalDateTime endTime) {
        String query = SELECT_SITTINGS_IN_RANGE_QUERY;
        Map<String, String> params = ImmutableMap.of(
                "startTime", startTime.toString(),
                "endTime", endTime.toString()
        );

        try {
            return jdbcTemplate.query(query, params, rowMapper);
        } catch (DataAccessException e) {
            LOGGER.error(String.format("Error executing query: %s with params: %s", query, params), e);
            return Collections.emptyList();
        }
    }

    class SittingRowMapper implements RowMapper<BathroomSittingDto> {

        @Override
        public BathroomSittingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("end_time").toLocalDateTime();

            BathroomSittingDto dto = new BathroomSittingDto();
            dto.setStartTime(startTime);
            dto.setEndTime(endTime);

            return dto;
        }
    }
}
