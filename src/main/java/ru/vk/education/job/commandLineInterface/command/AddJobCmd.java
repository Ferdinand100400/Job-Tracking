package ru.vk.education.job.commandLineInterface.command;

import ru.vk.education.job.service.JobService;
import ru.vk.education.job.web.dto.JobDto;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

// job VK java-backend --skills=java,Linux --exp=2
public class AddJobCmd implements Command {

    private final JobService jobService;

    public AddJobCmd(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public String name() {
        return "job";
    }

    @Override
    public void execute(String[] params) {
        jobService.addJob(new JobDto(name(params), company(params), tags(params), experience(params)));
    }

    private String name(String[] params) {
        return params[0];
    }

    private String company(String[] params) {
        return Arrays.stream(params)
                .filter(s -> s.startsWith("--company"))
                .findFirst()
                .map(s -> s.replace("--company=", ""))
                .orElseThrow();
    }

    private Set<String> tags(String[] params) {
        return Arrays.stream(params)
                .filter(s -> s.startsWith("--tags"))
                .findFirst()
                .map(s -> s.replace("--tags=", "").split(","))
                .map(arr -> Arrays.stream(arr).collect(Collectors.toSet()))
                .orElseThrow();
    }

    private int experience(String[] params) {
        return Arrays.stream(params)
                .filter(s -> s.startsWith("--exp"))
                .findFirst()
                .map(s -> s.replace("--exp=", ""))
                .map(Integer::parseInt)
                .orElseThrow();
    }
}
