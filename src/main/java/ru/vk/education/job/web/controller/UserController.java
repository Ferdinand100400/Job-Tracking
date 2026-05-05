package ru.vk.education.job.web.controller;

import org.springframework.web.bind.annotation.*;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.service.ServiceLink;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ServiceLink serviceLink;

    public UserController(ServiceLink serviceLink) {
        this.serviceLink = serviceLink;
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        System.out.println("add user: " + user);
        serviceLink.getUserService().addUser(user);
    }

    @GetMapping
    public List<User> getListUsers() {
        System.out.println(serviceLink.getUserService());
        return serviceLink.getUserService().getListUsers();
    }
}
