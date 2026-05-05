package ru.vk.education.job.commandLineInterface.command;

import ru.vk.education.job.domain.User;
import ru.vk.education.job.service.JobService;
import ru.vk.education.job.service.MatchOfUserToJobService;
import ru.vk.education.job.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsCmd implements Command {

    private final JobService jobService;
    private final MatchOfUserToJobService matchOfUserToJobService;
    private final UserService userService;

    public StatisticsCmd(JobService jobService, UserService userService, MatchOfUserToJobService matchOfUserToJobService) {
        this.jobService = jobService;
        this.userService = userService;
        this.matchOfUserToJobService = matchOfUserToJobService;
    }

    @Override
    public String name() {
        return "stat";
    }

    @Override
    public void execute(String[] params) {
        if (params[0].equals("--exp"))
            jobService.getListJobsWithExpLeastOfN(Integer.parseInt(params[1])).forEach(System.out::println);
        if (params[0].equals("--match"))
            matchOfUserToJobService.getListUserWithLeastNMatches(Integer.parseInt(params[1])).forEach(System.out::println);
        if (params[0].equals("--top-skills"))
            userService.topNSkills(Integer.parseInt(params[1])).forEach(System.out::println);
    }
}
