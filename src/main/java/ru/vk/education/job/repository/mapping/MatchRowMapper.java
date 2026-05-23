package ru.vk.education.job.repository.mapping;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.vk.education.job.domain.Job;
import ru.vk.education.job.domain.MatchOfUserToJob;
import ru.vk.education.job.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchRowMapper implements RowMapper<MatchOfUserToJob> {

    private final JdbcTemplate jdbcTemplate;

    public MatchRowMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MatchOfUserToJob mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long userId = rs.getLong("user_id");
        Long jobId = rs.getLong("job_id");
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
                new UserRowMapper(),
                userId);
        Job job = jdbcTemplate.queryForObject("SELECT * FROM jobs WHERE id = ?",
                new JobRowMapper(),
                jobId);
        return new MatchOfUserToJob(user, job, rs.getDouble("countMatch"));
    }
}
