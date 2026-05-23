package ru.vk.education.job.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.vk.education.job.domain.Job;
import ru.vk.education.job.domain.MatchOfUserToJob;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.repository.mapping.JobRowMapper;
import ru.vk.education.job.repository.mapping.MatchRowMapper;
import ru.vk.education.job.repository.mapping.UserRowMapper;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class MatchOfUserToJobJdbcRepository implements MatchOfUserToJobRepository {
    private final JdbcTemplate jdbcTemplate;

    public MatchOfUserToJobJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MatchOfUserToJob> findAllMatchOfUserToJob() {
        return jdbcTemplate.query("SELECT * FROM matchOfUserToJob", new MatchRowMapper(jdbcTemplate));
    }

    @Override
    public void save(MatchOfUserToJob matchOfUserToJob) {
        String sql = "INSERT INTO matchOfUserToJob (user_id, job_id, countMatch) VALUES (?, ?, ?)";

        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE name = ?",
                new UserRowMapper(),
                matchOfUserToJob.user().name());

        Job job = jdbcTemplate.queryForObject("SELECT * FROM jobs WHERE name = ?",
                new JobRowMapper(),
                matchOfUserToJob.job().name());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, user.id());
            ps.setLong(2, job.id());
            ps.setDouble(3, matchOfUserToJob.countMatch());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) matchOfUserToJob.setId(keyHolder.getKey().longValue());
    }
}
