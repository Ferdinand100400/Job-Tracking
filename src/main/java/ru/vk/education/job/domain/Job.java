package ru.vk.education.job.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class Job {
    @JsonProperty
    private final String name;
    @JsonProperty
    private final String company;
    @JsonProperty
    private final Set<String> tags;
    @JsonProperty
    private final Integer experience;

    @JsonCreator
    public Job(@JsonProperty("name") String name, @JsonProperty("company") String company, @JsonProperty("tags") Set<String> tags, @JsonProperty("experience") Integer experience) {
        this.name = name;
        this.company = company;
        this.tags = tags;
        this.experience = experience;
    }

    // Если пользователю по опыту подходит вакансия, то true
    public boolean isCheckExperienceToUser(User user) {
        return user.isCheckExperienceToJob(experience);
    }

    // Если пользователю по опыту подходит вакансия, то true
    public boolean isCheckExperienceLeastN(int n) {
        return experience >= n;
    }

    // Проверка имя работы совпадает ли с именем переданной работы
    public boolean isJobExists(Job job) {
        return name.equals(job.name);
    }

    public String name() {
        return name;
    }

    public Set<String> tags() {
        return tags;
    }

    @Override
    public String toString() {
        return name + " at " + company;
    }
}