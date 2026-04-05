package ru.vk.education.job.service;

import ru.vk.education.job.domain.Job;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JobService {
    private final List<Job> jobs;
    private final ServiceLink serviceLink;
    
    public JobService(ServiceLink serviceLink) {
        jobs = new ArrayList<>();
        this.serviceLink = serviceLink;
    }

    public void addJob(Job job) {
        if (job == null) return;
        for (Job j : jobs) {
            if (j.isJobExists(job)) return;
        }
        serviceLink.matchOfUserToJobService.addMatch(serviceLink.getListUsers(), job);
        jobs.add(job);
    }

    public List<Job> getListJobs() {
        return jobs.stream()
                .sorted(Comparator.comparing(Job::name))
                .collect(Collectors.toList());
    }

    // Получение списка вакансий с опытом не менее указанного (n)
    public List<Job> getListJobsWithExpLeastOfN(int n) {
        return jobs.stream()
                .filter(j -> j.isCheckExperienceLeastN(n))
                .sorted(Comparator.comparing(Job::name))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        List<Job> jobs = getListJobs();
        String res = "";
        for (int i = 0; i < jobs.size(); i++) {
            res = res.concat(jobs.get(i).toString());
            if (i != jobs.size() - 1) res = res.concat("\n");
        }
        return res;
    }
}