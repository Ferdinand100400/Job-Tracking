package ru.vk.education.job.web.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.vk.education.job.domain.User;

import java.util.Objects;
import java.util.Set;

public class UserDto {
    @JsonProperty
    private final String name;
    @JsonProperty
    private final Set<String> skills;
    @JsonProperty
    private final Integer experience;

    @JsonCreator
    public UserDto(@JsonProperty("name") String name, @JsonProperty("skills") Set<String> skills, @JsonProperty("experience") Integer experience) {
        this.name = name;
        this.skills = skills;
        this.experience = experience;
    }

    // Если пользователю по опыту подходит вакансия, то true
    public boolean isCheckExperienceToJob(Integer experienceJob) {
        return experience >= experienceJob;
    }

    // Проверка имя текущего пользователя совпадает ли с именем другого пользователя
    public boolean isUserExists(UserDto userDto) {
        return name.equals(userDto.name);
    }

    public boolean isUserExists(String userName) {
        return name.equals(userName);
    }

    public String name() {
        return name;
    }

    public Set<String> skills() {
        return skills;
    }

    public Integer experience() {
        return experience;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserDto userDto)) return false;
        return Objects.equals(name, userDto.name) && Objects.equals(skills, userDto.skills) && Objects.equals(experience, userDto.experience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, skills, experience);
    }

    @Override
    public String toString() {
        String printSkills = "";
        for (String skill : this.skills) {
            printSkills = printSkills.concat(skill + ",");
        }
        printSkills = printSkills.substring(0, printSkills.length() - 1);
        return name + " " + printSkills + " " + experience;
    }
}
