package ru.vk.education.job.web.controller;

import org.springframework.web.bind.annotation.*;
import ru.vk.education.job.service.ServiceLink;
import ru.vk.education.job.web.dto.JobDto;
import ru.vk.education.job.web.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping("/suggest")
public class SuggestController {

    private final ServiceLink serviceLink;

    public SuggestController(ServiceLink serviceLink) {
        this.serviceLink = serviceLink;
    }

    @GetMapping
    public List<JobDto> getTwoJobsMatchedForUser(@RequestParam("name") String userName) {
        UserDto user = serviceLink.getUserService().getUserByName(userName);
        System.out.println("2 вакансии подходящие пользователю " + userName + ": "
                + serviceLink.getMatchOfUserToJobService().getJobsForUser(user, 2));
        return serviceLink.getMatchOfUserToJobService().getJobsForUser(user, 2);
    }
}
