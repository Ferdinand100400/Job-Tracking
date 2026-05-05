package ru.vk.education.job.web.controller;

import org.springframework.web.bind.annotation.*;
import ru.vk.education.job.domain.Job;
import ru.vk.education.job.service.ServiceLink;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    private final ServiceLink serviceLink;

    public JobController(ServiceLink serviceLink) {
        this.serviceLink = serviceLink;
    }

    @PostMapping
    public void addJob(@RequestBody Job job) {
        System.out.println("add job: " + job);
        serviceLink.getJobService().addJob(job);
    }

    @GetMapping
    public List<Job> getListJobs() {
        System.out.println(serviceLink.getJobService());
        return serviceLink.getJobService().getListJobs();
    }
}
