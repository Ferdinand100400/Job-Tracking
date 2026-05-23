package ru.vk.education.job.repository;

import ru.vk.education.job.domain.Job;

import java.util.List;

public interface JobRepository {

    List<Job> findJobs();

    Job findJobById(Long id);

    Job findJobByName(String name);

    void save(Job job);

}
