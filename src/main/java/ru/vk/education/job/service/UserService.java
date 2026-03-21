package ru.vk.education.job.service;

import ru.vk.education.job.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users;
    private final ServiceLink serviceLink;
    
    public UserService(ServiceLink serviceLink) {
        users = new ArrayList<>();
        this.serviceLink = serviceLink;
    }

    public void addUser(User user) {
        if (user == null) return;
        for (User u : users) {
            if (u.isUserExists(user)) return;
        }
        serviceLink.matchOfUserToJobService.addMatch(user, serviceLink.getListJobs());
        users.add(user);
    }

    public List<User> getListUsers() {
        return users;
    }

    public User getUserByName(String name) {
        for (User user : users) {
            if (user.isUserExists(name)) return user;
        }
        return null;
    }

    @Override
    public String toString() {
       String res = "";
       for (int i = 0; i < users.size(); i++) {
           res = res.concat(users.get(i).toString());
           if (i != users.size() - 1) res = res.concat("\n");
       }
       return res;
    }
}