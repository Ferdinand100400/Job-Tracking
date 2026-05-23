package ru.vk.education.job.service;

import org.springframework.stereotype.Service;
import ru.vk.education.job.web.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

@Service
public class SchedulerService implements Runnable {

    private final ServiceLink serviceLink;

    public SchedulerService(ServiceLink serviceLink) {
        this.serviceLink = serviceLink;
    }

    @Override
    public void run() {
        Map<String, String> bestOfferForUsers = new HashMap<>();
        for (UserDto user : serviceLink.getUserService().getListUsers()) {
            try {
                bestOfferForUsers.put(user.name(), serviceLink.getMatchOfUserToJobService().getBestJobForUser(user).toString());
            }
            catch (IllegalArgumentException e) {
                bestOfferForUsers.put(user.name(), "нет подходящего предложения");
            }
        }
        printBestOfferForUsers(bestOfferForUsers);
    }

    private void printBestOfferForUsers(Map<String, String> bestOfferForUsers) {
        for (Map.Entry<String, String> bestOffer : bestOfferForUsers.entrySet()) {
            System.out.println(bestOffer.getKey() + ", лучшее предложение — " + bestOffer.getValue());
        }
    }
}
