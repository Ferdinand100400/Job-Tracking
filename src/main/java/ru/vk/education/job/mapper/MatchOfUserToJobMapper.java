package ru.vk.education.job.mapper;

import ru.vk.education.job.domain.Job;
import ru.vk.education.job.domain.MatchOfUserToJob;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.web.dto.JobDto;
import ru.vk.education.job.web.dto.MatchOfUserToJobDto;
import ru.vk.education.job.web.dto.UserDto;

import java.util.List;

public class MatchOfUserToJobMapper {

    public static MatchOfUserToJob dtoToDomain(MatchOfUserToJobDto matchOfUserToJobDto) {
        User user = UserMapper.dtoToDomain(matchOfUserToJobDto.userDto());
        Job job = JobMapper.dtoToDomain(matchOfUserToJobDto.jobDto());
        return new MatchOfUserToJob(user, job, matchOfUserToJobDto.countMatch());
    }

    public static MatchOfUserToJobDto domainToDto(MatchOfUserToJob matchOfUserToJob) {
        UserDto userDto = UserMapper.domainToDto(matchOfUserToJob.user());
        JobDto jobDto = JobMapper.domainToDto(matchOfUserToJob.job());
        return new MatchOfUserToJobDto(userDto, jobDto);
    }

    public static List<MatchOfUserToJobDto> domainListToDtoList(List<MatchOfUserToJob> matchesOfUserToJobs) {
        return matchesOfUserToJobs.stream()
                .map(MatchOfUserToJobMapper::domainToDto)
                .toList();
    }
}
