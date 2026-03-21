package ru.vk.education.job.domain;

public class  MatchOfUserToJob {
    private final User user;
    private final Job job;
    private final double countMatch;

    public MatchOfUserToJob(User user, Job job) {
        this.user = user;
        this.job = job;
        this.countMatch = calculateMatch(user, job);
    }

    public Job getJobIfUserOrNull(User user) {
        if (this.user.equals(user)) return job;
        return null;
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
}