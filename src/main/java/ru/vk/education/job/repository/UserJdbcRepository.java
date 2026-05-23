package ru.vk.education.job.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.repository.mapping.UserRowMapper;

import java.sql.PreparedStatement;;
import java.util.List;

@Repository
public class UserJdbcRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findUsers() {
        return jdbcTemplate.query("SELECT * FROM users",
                new UserRowMapper()
        );
    }

    public User findUserById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
                new UserRowMapper(),
                id
        );
    }

    public User findUserByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE name = ?",
                new UserRowMapper(),
                name
        );
    }

    public void saveUser(User user) {
        String sql = "INSERT INTO users (name, skills, experience) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.name());
            ps.setString(2, String.join(", ", user.skills()));
            ps.setInt(3, user.experience());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) user.setId(keyHolder.getKey().longValue());
    }
}
