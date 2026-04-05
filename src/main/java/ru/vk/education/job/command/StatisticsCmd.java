package ru.vk.education.job.command;

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
            topNSkills(userService.getListUsers(), Integer.parseInt(params[1])).forEach(System.out::println);
    }

    // Топ N скиллов среди всех пользователей (скиллы, которые чаще всего встречаются)
    private List<String> topNSkills(List<User> users, int n) {
        return users.stream()
                .flatMap(user -> user.skills().stream())
                .collect(Collectors.groupingBy(skill -> skill, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }
}
