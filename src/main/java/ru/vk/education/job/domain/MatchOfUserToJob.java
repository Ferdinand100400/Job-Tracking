package ru.vk.education.job.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class  MatchOfUserToJob {
    @JsonProperty
    private final User user;
    @JsonProperty
    private final Job job;
    @JsonProperty
    private final double countMatch;

    @JsonCreator
    public MatchOfUserToJob(@JsonProperty("user") User user, @JsonProperty("job") Job job) throws IllegalArgumentException {
        double countMatch = calculateMatch(user, job);
        if (countMatch == 0) throw new IllegalArgumentException("");
        this.countMatch = countMatch;
        this.user = user;
        this.job = job;
    }

    public Job getJobIfUserOrNull(User user) {
        if (this.user.equals(user)) return job;
        return null;
    }

    public User user() {
        return user;
    }

    // Расчет соответствия пользователя вакансии
    private double calculateMatch(User user, Job job) {
        int countMatch = 0;
        for (String skill : user.skills()) {
            if (job.tags().contains(skill)) countMatch++;
        }
        if (!job.isCheckExperienceToUser(user)) countMatch /= 2;
        return countMatch;
    }

    public double getCountMatch() {
        return countMatch;
    }

    @Override
    public String toString() {
        return "MatchOfUserToJob{" +
                "user=" + user +
                ", job=" + job +
                ", countMatch=" + countMatch +
                '}';
    }
}