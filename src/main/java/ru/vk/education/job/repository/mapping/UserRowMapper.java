package ru.vk.education.job.repository.mapping;

import org.springframework.jdbc.core.RowMapper;
import ru.vk.education.job.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper extends GeneralMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("id"), rs.getString("name"), stringToSet(rs.getString("skills")), rs.getInt("experience"));
    }

}
