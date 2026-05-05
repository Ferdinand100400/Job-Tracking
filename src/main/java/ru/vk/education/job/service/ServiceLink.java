package ru.vk.education.job.service;

import org.springframework.stereotype.Service;
import ru.vk.education.job.domain.Job;
import ru.vk.education.job.domain.User;

import java.util.List;

@Service
public class ServiceLink {
    private final UserService userService;
    private final JobService jobService;
    private final MatchOfUserToJobService matchOfUserToJobService;

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
