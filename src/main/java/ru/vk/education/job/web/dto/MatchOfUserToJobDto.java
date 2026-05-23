package ru.vk.education.job.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class MatchOfUserToJobDto {
    @JsonProperty
    private final UserDto userDto;
    @JsonProperty
    private final JobDto jobDto;
    @JsonProperty
    private final double countMatch;

    @JsonCreator
    public MatchOfUserToJobDto(@JsonProperty("userDto") UserDto userDto, @JsonProperty("jobDto") JobDto jobDto) throws IllegalArgumentException {
        double countMatch = calculateMatch(userDto, jobDto);
        if (countMatch == 0) throw new IllegalArgumentException("");
        this.countMatch = countMatch;
        this.userDto = userDto;
        this.jobDto = jobDto;
    }

    public JobDto getJobIfUserOrNull(UserDto userDto) {
        if (this.userDto.equals(userDto)) return jobDto;
        return null;
    }

    public UserDto userDto() {
        return userDto;
    }

    public JobDto jobDto() {
        return jobDto;
    }

    // Расчет соответствия пользователя вакансии
    private double calculateMatch(UserDto userDto, JobDto jobDto) {
        int countMatch = 0;
        for (String skill : userDto.skills()) {
            if (jobDto.tags().contains(skill)) countMatch++;
        }
        if (!jobDto.isCheckExperienceToUser(userDto)) countMatch /= 2;
        return countMatch;
    }

    public double countMatch() {
        return countMatch;
    }

    @Override
    public String toString() {
        return "MatchOfUserToJob{" +
                "user=" + userDto +
                ", job=" + jobDto +
                ", countMatch=" + countMatch +
                '}';
    }
}
