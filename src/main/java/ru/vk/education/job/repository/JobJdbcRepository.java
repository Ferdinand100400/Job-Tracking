package ru.vk.education.job.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.vk.education.job.domain.Job;
import ru.vk.education.job.repository.mapping.JobRowMapper;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JobJdbcRepository implements JobRepository {

    private final JdbcTemplate jdbcTemplate;

    public JobJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Job> findJobs() {
        return jdbcTemplate.query("SELECT * FROM jobs",
                new JobRowMapper()
        );
    }

    public Job findJobById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM jobs WHERE id = ?",
                new JobRowMapper(),
                id
        );
    }

    public Job findJobByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM jobs WHERE name = ?",
                new JobRowMapper(),
                name
        );
    }

    public void save(Job job) {
        String sql = "INSERT INTO jobs (name, company, tags, experience) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, job.name());
            ps.setString(2, job.company());
            ps.setString(3, String.join(", ", job.tags()));
            ps.setInt(4, job.experience());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) job.setId(keyHolder.getKey().longValue());
    }
}
