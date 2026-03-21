package ru.vk.education.job.service;

import ru.vk.education.job.domain.Job;
import ru.vk.education.job.domain.User;

import java.util.List;

public class ServiceLink {
    UserService userService;
    JobService jobService;
    MatchOfUserToJobService matchOfUserToJobService;

    public ServiceLink() {
        userService = new UserService(this);
        jobService = new JobService(this);
        matchOfUserToJobService = new MatchOfUserToJobService();
    }

    public UserService getUserService() {
        return userService;
    }

    public JobService getJobService() {
        return jobService;
    }

    public MatchOfUserToJobService getMatchOfUserToJobService() {
        return matchOfUserToJobService;
    }

    public List<Job> getListJobs() {
        return jobService.getListJobs();
    }

    public List<User> getListUsers() {
        return userService.getListUsers();
    }
}
