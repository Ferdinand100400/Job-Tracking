package ru.vk.education.job.web.controller;

import org.springframework.web.bind.annotation.*;
import ru.vk.education.job.service.ServiceLink;
import ru.vk.education.job.web.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ServiceLink serviceLink;

    public UserController(ServiceLink serviceLink) {
        this.serviceLink = serviceLink;
    }

    @PostMapping
    public void addUser(@RequestBody UserDto userDto) {
        System.out.println("add user: " + userDto);
        serviceLink.getUserService().addUser(userDto);
    }

    @GetMapping
    public List<UserDto> getListUsers() {
        System.out.println(serviceLink.getUserService());
        return serviceLink.getUserService().getListUsers();
    }
}
