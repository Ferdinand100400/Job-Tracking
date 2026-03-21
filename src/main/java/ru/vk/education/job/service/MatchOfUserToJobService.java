package ru.vk.education.job.service;

import ru.vk.education.job.domain.Job;
import ru.vk.education.job.domain.MatchOfUserToJob;
import ru.vk.education.job.domain.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class  MatchOfUserToJobService {
    private final List<MatchOfUserToJob> matchesOfUserToJob;

    public MatchOfUserToJobService() {
        matchesOfUserToJob = new ArrayList<>();
    }

    // Добавление новых соответствий в результате добавления нового пользователя
    public void addMatch(User newUser, List<Job> jobs) {
        for (Job job : jobs) {
            matchesOfUserToJob.add(new MatchOfUserToJob(newUser, job));
        }
    }

    // Добавление новых соответствий в результате добавления новой работы
    public void addMatch(List<User> users, Job newJob) {
        for (User user : users) {
            matchesOfUserToJob.add(new MatchOfUserToJob(user, newJob));
        }
    }

    // Получение 2х вакансий подходящие пользователю
    public List<Job> getTwoJobForUser(User user) {
        List<Job> jobs = new ArrayList<>();
        int limit = 0;
        for (MatchOfUserToJob matchOfUserToJob : sortedMatchesOfUserToJob(matchesOfUserToJob)) {
            Job job = matchOfUserToJob.getJobIfUserOrNull(user);
            if (job != null && limit < 2) {
                jobs.add(job);
                limit++;
            }
        }
        return jobs;
    }

    // Отсортированный список по убыванию величины совпадения
    private List<MatchOfUserToJob> sortedMatchesOfUserToJob(List<MatchOfUserToJob> matchesOfUserToJob) {
        return matchesOfUserToJob.stream()
                .sorted(Comparator.comparing(MatchOfUserToJob::getCountMatch).reversed())
                .collect(Collectors.toList());
    }
}