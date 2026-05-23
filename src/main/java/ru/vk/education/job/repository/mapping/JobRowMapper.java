package ru.vk.education.job.repository.mapping;

import org.springframework.jdbc.core.RowMapper;
import ru.vk.education.job.domain.Job;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JobRowMapper extends GeneralMapper implements RowMapper<Job> {

    @Override
    public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Job(rs.getLong("id"), rs.getString("name"), rs.getString("company"), stringToSet(rs.getString("tags")), rs.getInt("experience"));
    }
}
