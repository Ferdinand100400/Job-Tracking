package ru.vk.education.job.domain;

import java.util.Set;

public class Job {
    private final String name;
    private final String company;
    private final Set<String> tags;
    private final Integer experience;

    public Job(String name, String company, Set<String> tags, Integer experience) {
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