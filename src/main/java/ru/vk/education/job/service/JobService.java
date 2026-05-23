package ru.vk.education.job.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.vk.education.job.domain.Job;
import ru.vk.education.job.mapper.JobMapper;
import ru.vk.education.job.repository.JobRepository;
import ru.vk.education.job.web.dto.JobDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepo;
     private final ServiceLink serviceLink;

    public JobService(JobRepository jobRepo, ServiceLink serviceLink) {
        this.jobRepo = jobRepo;
        this.serviceLink = serviceLink;
    }

    public void addJob(JobDto jobDto) {
        if (jobDto == null) return;
        try {
            jobRepo.findJobByName(jobDto.name());
        } catch (EmptyResultDataAccessException e) {
            jobRepo.save(JobMapper.dtoToDomain(jobDto));
            serviceLink.getMatchOfUserToJobService().addMatch(serviceLink.getListUsers(), jobDto);
        }
    }

    public List<JobDto> getListJobs() {
        return jobRepo.findJobs().stream()
                .map(JobMapper::domainToDto)
                .sorted(Comparator.comparing(JobDto::name))
                .collect(Collectors.toList());
    }

    // Получение списка вакансий с опытом не менее указанного (n)
    public List<JobDto> getListJobsWithExpLeastOfN(int n) {
        return jobRepo.findJobs().stream()
                .map(JobMapper::domainToDto)
                .filter(j -> j.isCheckExperienceLeastN(n))
                .sorted(Comparator.comparing(JobDto::name))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        List<JobDto> jobs = getListJobs();
        String res = "";
        for (int i = 0; i < jobs.size(); i++) {
            res = res.concat(jobs.get(i).toString());
            if (i != jobs.size() - 1) res = res.concat("\n");
        }
        return res;
    }
}