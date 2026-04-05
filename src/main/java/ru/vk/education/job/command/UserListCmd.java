package ru.vk.education.job.command;

import ru.vk.education.job.service.UserService;

public class UserListCmd implements Command {

    private final UserService userService;

    public UserListCmd(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String name() {
        return "user-list";
    }

    @Override
    public void execute(String[] params) {
        System.out.println(userService);
    }
}
