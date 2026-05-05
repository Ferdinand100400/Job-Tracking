package ru.vk.education.job.commandLineInterface.command;

import ru.vk.education.job.service.JobService;

public class JobListCmd implements Command {

    private final JobService jobService;

    public JobListCmd(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public String name() {
        return "job-list";
    }

    @Override
    public void execute(String[] params) {
        System.out.println(jobService);
    }
}
