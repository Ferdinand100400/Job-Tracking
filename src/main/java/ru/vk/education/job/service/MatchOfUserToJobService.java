package ru.vk.education.job.service;

import org.springframework.stereotype.Service;
import ru.vk.education.job.mapper.MatchOfUserToJobMapper;
import ru.vk.education.job.repository.MatchOfUserToJobRepository;
import ru.vk.education.job.web.dto.JobDto;
import ru.vk.education.job.web.dto.MatchOfUserToJobDto;
import ru.vk.education.job.web.dto.UserDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class  MatchOfUserToJobService {
    private final MatchOfUserToJobRepository matchesOfUserToJobRepo;

    public MatchOfUserToJobService(MatchOfUserToJobRepository matchesOfUserToJobRepo) {
        this.matchesOfUserToJobRepo = matchesOfUserToJobRepo;
    }

    // Добавление новых соответствий в результате добавления нового пользователя
    public void addMatch(UserDto newUser, List<JobDto> jobs) {
        for (JobDto job : jobs) {
            try {
                matchesOfUserToJobRepo.save(MatchOfUserToJobMapper.dtoToDomain(new MatchOfUserToJobDto(newUser, job)));
            } catch (IllegalArgumentException e) {
                return;
            }
        }
    }

    // Добавление новых соответствий в результате добавления новой работы
    public void addMatch(List<UserDto> users, JobDto newJob) {
        for (UserDto user : users) {
            try {
                matchesOfUserToJobRepo.save(MatchOfUserToJobMapper.dtoToDomain(new MatchOfUserToJobDto(user, newJob)));
            } catch (IllegalArgumentException e) {
                return;
            }
        }
    }

    // Получение countJobs вакансий подходящие пользователю в порядке убывания метча
    public List<JobDto> getJobsForUser(UserDto userDto, int countJobs) {
        List<JobDto> jobs = new ArrayList<>();
        int limit = 0;
        for (MatchOfUserToJobDto matchOfUserToJobDto : sortedMatchesOfUserToJob(MatchOfUserToJobMapper.domainListToDtoList(matchesOfUserToJobRepo.findAllMatchOfUserToJob()))) {
            JobDto jobDto = matchOfUserToJobDto.getJobIfUserOrNull(userDto);
            if (jobDto != null && limit < countJobs) {
                jobs.add(jobDto);
                limit++;
            }
        }
        return jobs;
    }

    public JobDto getBestJobForUser(UserDto userDto) {
        try {
            return getJobsForUser(userDto, 1).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("");
        }
    }

    // Получение списка пользователей у которых есть не менее чем N мэтчей
    public List<UserDto> getListUserWithLeastNMatches(int n) {
        List<MatchOfUserToJobDto> matchesOfUserToJob = MatchOfUserToJobMapper.domainListToDtoList(matchesOfUserToJobRepo.findAllMatchOfUserToJob());
        return matchesOfUserToJob.stream()
                .collect(Collectors.groupingBy(MatchOfUserToJobDto::userDto, Collectors.counting()))
                .entrySet().stream()
                .filter(countMatch -> countMatch.getValue() >= n)
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(UserDto::name))
                .collect(Collectors.toList());
    }

    // Отсортированный список по убыванию величины совпадения
    private List<MatchOfUserToJobDto> sortedMatchesOfUserToJob(List<MatchOfUserToJobDto> matchesOfUserToJobDto) {
        return matchesOfUserToJobDto.stream()
                .sorted(Comparator.comparing(MatchOfUserToJobDto::countMatch).reversed())
                .collect(Collectors.toList());
    }
}