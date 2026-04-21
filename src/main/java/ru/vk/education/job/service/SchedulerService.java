package ru.vk.education.job.service;

import ru.vk.education.job.domain.User;

import java.util.HashMap;
import java.util.Map;

public class SchedulerService implements Runnable {

    private final UserService userService;
    private final MatchOfUserToJobService matchOfUserToJobService;

    public SchedulerService(UserService userService, MatchOfUserToJobService matchOfUserToJobService) {
        this.userService = userService;
        this.matchOfUserToJobService = matchOfUserToJobService;
    }

    @Override
    public void run() {
        Map<String, String> bestOfferForUsers = new HashMap<>();
        for (User user : userService.getListUsers()) {
            try {
                bestOfferForUsers.put(user.name(), matchOfUserToJobService.getBestJobForUser(user).toString());
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
