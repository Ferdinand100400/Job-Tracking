package ru.vk.education.job.mapper;

import ru.vk.education.job.domain.Job;
import ru.vk.education.job.web.dto.JobDto;

public class JobMapper {

    public static Job dtoToDomain(JobDto jobDto) {
        return new Job(jobDto.name(), jobDto.company(), jobDto.tags(), jobDto.experience());
    }

    public static JobDto domainToDto(Job job) {
        return new JobDto(job.name(), job.company(), job.tags(), job.experience());
    }
}
