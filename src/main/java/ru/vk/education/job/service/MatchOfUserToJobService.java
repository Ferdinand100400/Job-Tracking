package ru.vk.education.job.service;

import ru.vk.education.job.domain.Job;
import ru.vk.education.job.domain.MatchOfUserToJob;
import ru.vk.education.job.domain.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class  MatchOfUserToJobService {
    private final List<MatchOfUserToJob> matchesOfUserToJob;

    public MatchOfUserToJobService() {
        matchesOfUserToJob = new ArrayList<>();
    }

    // Добавление новых соответствий в результате добавления нового пользователя
    public void addMatch(User newUser, List<Job> jobs) {
        for (Job job : jobs) {
            try {
                matchesOfUserToJob.add(new MatchOfUserToJob(newUser, job));
            } catch (IllegalArgumentException e) {
                return;
            }
        }
    }

    // Добавление новых соответствий в результате добавления новой работы
    public void addMatch(List<User> users, Job newJob) {
        for (User user : users) {
            try {
                matchesOfUserToJob.add(new MatchOfUserToJob(user, newJob));
            } catch (IllegalArgumentException e) {
                return;
            }
        }
    }

    // Получение countJobs вакансий подходящие пользователю в порядке убывания метча
    public List<Job> getJobsForUser(User user, int countJobs) {
        List<Job> jobs = new ArrayList<>();
        int limit = 0;
        for (MatchOfUserToJob matchOfUserToJob : sortedMatchesOfUserToJob(matchesOfUserToJob)) {
            Job job = matchOfUserToJob.getJobIfUserOrNull(user);
            if (job != null && limit < countJobs) {
                jobs.add(job);
                limit++;
            }
        }
        return jobs;
    }

    public Job getBestJobForUser(User user) {
        try {
            return getJobsForUser(user, 1).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("");
        }
    }

    // Получение списка пользователей у которых есть не менее чем N мэтчей
    public List<User> getListUserWithLeastNMatches(int n) {
        return matchesOfUserToJob.stream()
                .collect(Collectors.groupingBy(MatchOfUserToJob::user, Collectors.counting()))
                .entrySet().stream()
                .filter(countMatch -> countMatch.getValue() >= n)
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(User::name))
                .collect(Collectors.toList());
    }

    // Отсортированный список по убыванию величины совпадения
    private List<MatchOfUserToJob> sortedMatchesOfUserToJob(List<MatchOfUserToJob> matchesOfUserToJob) {
        return matchesOfUserToJob.stream()
                .sorted(Comparator.comparing(MatchOfUserToJob::getCountMatch).reversed())
                .collect(Collectors.toList());
    }
}