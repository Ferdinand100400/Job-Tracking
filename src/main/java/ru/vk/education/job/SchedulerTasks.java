package ru.vk.education.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.vk.education.job.service.SchedulerService;

@Component
public class SchedulerTasks {
    private final SchedulerService schedulerService;

    public SchedulerTasks(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Scheduled(fixedRate = 60_000)
    public void bestOffer() {
        schedulerService.run();
    }
}
