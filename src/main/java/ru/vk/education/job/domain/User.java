package ru.vk.education.job.domain;

import java.util.Set;

public class User {
    private final String name;
    private final Set<String> skills;
    private final Integer experience;

    public User(String name, Set<String> skills, Integer experience) {
        this.name = name;
        this.skills = skills;
        this.experience = experience;
    }

    // Если пользователю по опыту подходит вакансия, то true
    public boolean isCheckExperienceToJob(Integer experienceJob) {
        return experience >= experienceJob;
    }

    // Проверка имя текущего пользователя совпадает ли с именем другого пользователя
    public boolean isUserExists(User user) {
        return name.equals(user.name);
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