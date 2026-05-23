package ru.vk.education.job.domain;

public class  MatchOfUserToJob {
    private Long id;
    private final User user;
    private final Job job;
    private final double countMatch;

    public MatchOfUserToJob(Long id, User user, Job job, double countMatch) {
        this.id = id;
        this.job = job;
        this.user = user;
        this.countMatch = countMatch;
    }

    public MatchOfUserToJob(User user, Job job, double countMatch) {
        this(0L, user, job, countMatch);
    }

    public User user() {
        return user;
    }

    public Job job() {
        return job;
    }

    public double countMatch() {
        return countMatch;
    }

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}