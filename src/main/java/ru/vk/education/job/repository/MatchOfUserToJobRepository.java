package ru.vk.education.job.repository;

import ru.vk.education.job.domain.MatchOfUserToJob;

import java.util.List;

public interface MatchOfUserToJobRepository {
    List<MatchOfUserToJob> findAllMatchOfUserToJob();
    void save(MatchOfUserToJob matchOfUserToJob);
}
