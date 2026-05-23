package ru.vk.education.job.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vk.education.job.service.ServiceLink;
import ru.vk.education.job.web.dto.JobDto;
import ru.vk.education.job.web.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping("/stat")
public class StatController {

    private final ServiceLink serviceLink;

    public StatController(ServiceLink serviceLink) {
        this.serviceLink = serviceLink;
    }

    @GetMapping("/exp")
    public List<JobDto> getListJobsWithExpLeastOfN(@RequestParam(value = "exp") int exp) {
        System.out.println("Список вакансий с опытом не менее " + exp + ": "
                + serviceLink.getJobService().getListJobsWithExpLeastOfN(exp));
        return serviceLink.getJobService().getListJobsWithExpLeastOfN(exp);
    }

    @GetMapping("/match")
    public List<UserDto> getListUserWithLeastNMatches(@RequestParam(value = "countMatches") int n) {
        System.out.println("Список пользователей у которых есть не менее чем N мэтчей: "
                + serviceLink.getMatchOfUserToJobService().getListUserWithLeastNMatches(n));
        return serviceLink.getMatchOfUserToJobService().getListUserWithLeastNMatches(n);
    }

    @GetMapping("/top-skills")
    public List<String> getTopNSkills(@RequestParam(value = "countSkills") int n) {
        System.out.println("Топ n скиллов среди всех пользователей: "
                + serviceLink.getUserService().topNSkills(n));
        return serviceLink.getUserService().topNSkills(n);
    }
}
