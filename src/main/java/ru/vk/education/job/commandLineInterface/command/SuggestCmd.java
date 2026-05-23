package ru.vk.education.job.commandLineInterface.command;

import ru.vk.education.job.service.MatchOfUserToJobService;
import ru.vk.education.job.service.UserService;
import ru.vk.education.job.web.dto.JobDto;
import ru.vk.education.job.web.dto.UserDto;

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
        UserDto u = userService.getUserByName(params[0]);
        if (u != null) {
            List<JobDto> jobs = matchOfUserToJobService.getJobsForUser(u, 2);
            for (JobDto j : jobs)
                System.out.println(j);
        }
    }
}
