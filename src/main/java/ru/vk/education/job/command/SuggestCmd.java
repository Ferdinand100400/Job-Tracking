package ru.vk.education.job.command;

import ru.vk.education.job.domain.Job;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.service.MatchOfUserToJobService;
import ru.vk.education.job.service.UserService;

import java.util.List;

public class SuggestCmd implements Command {

    UserService userService;
    MatchOfUserToJobService matchOfUserToJobService;

    public SuggestCmd(UserService userService, MatchOfUserToJobService matchOfUserToJobService) {
        this.userService = userService;
        this.matchOfUserToJobService = matchOfUserToJobService;
    }

    @Override
    public String name() {
        return "suggest";
    }

    @Override
    public void execute(String[] params) {
        User u = userService.getUserByName(params[0]);
        if (u != null) {
            List<Job> jobs = matchOfUserToJobService.getJobsForUser(u, 2);
            for (Job j : jobs)
                System.out.println(j);
        }
    }
}
