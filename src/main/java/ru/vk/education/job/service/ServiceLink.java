package ru.vk.education.job.service;

import org.springframework.stereotype.Service;
import ru.vk.education.job.repository.JobRepository;
import ru.vk.education.job.repository.MatchOfUserToJobJdbcRepository;
import ru.vk.education.job.repository.UserRepository;
import ru.vk.education.job.web.dto.JobDto;
import ru.vk.education.job.web.dto.UserDto;

import java.util.List;

@Service
public class ServiceLink {
    private final UserService userService;
    private final JobService jobService;
    private final MatchOfUserToJobService matchOfUserToJobService;

    public ServiceLink(UserRepository userRepo, JobRepository jobRepo, MatchOfUserToJobJdbcRepository matchOfUserToJobJdbcRepo) {
        userService = new UserService(userRepo, this);
        jobService = new JobService(jobRepo, this);
        matchOfUserToJobService = new MatchOfUserToJobService(matchOfUserToJobJdbcRepo);
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

    public List<JobDto> getListJobs() {
        return jobService.getListJobs();
    }

    public List<UserDto> getListUsers() {
        return userService.getListUsers();
    }
}
