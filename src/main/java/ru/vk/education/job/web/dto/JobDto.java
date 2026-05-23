package ru.vk.education.job.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.vk.education.job.domain.Job;
import ru.vk.education.job.domain.User;

import java.util.Set;

public class JobDto {
    @JsonProperty
    private final String name;
    @JsonProperty
    private final String company;
    @JsonProperty
    private final Set<String> tags;
    @JsonProperty
    private final Integer experience;

    @JsonCreator
    public JobDto(@JsonProperty("name") String name, @JsonProperty("company") String company, @JsonProperty("tags") Set<String> tags, @JsonProperty("experience") Integer experience) {
        this.name = name;
        this.company = company;
        this.tags = tags;
        this.experience = experience;
    }

    // Если пользователю по опыту подходит вакансия, то true
    public boolean isCheckExperienceToUser(UserDto userDto) {
        return userDto.isCheckExperienceToJob(experience);
    }

    // Если пользователю по опыту подходит вакансия, то true
    public boolean isCheckExperienceLeastN(int n) {
        return experience >= n;
    }

    // Проверка имя работы совпадает ли с именем переданной работы
    public boolean isJobExists(JobDto jobDto) {
        return name.equals(jobDto.name);
    }

    public String name() {
        return name;
    }

    public String company() {
        return company;
    }

    public Set<String> tags() {
        return tags;
    }

    public Integer experience() {
        return experience;
    }

    @Override
    public String toString() {
        return name + " at " + company;
    }
}
