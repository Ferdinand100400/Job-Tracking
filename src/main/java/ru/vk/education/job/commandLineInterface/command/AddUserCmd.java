package ru.vk.education.job.commandLineInterface.command;

import ru.vk.education.job.service.UserService;
import ru.vk.education.job.web.dto.UserDto;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

// user alice --skills=java,C++,bash --exp=4
public class AddUserCmd implements Command {

    private final UserService userService;

    public AddUserCmd(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String name() {
        return "user";
    }

    @Override
    public void execute(String[] params) {
        userService.addUser(new UserDto(name(params), skills(params), experience(params)));
    }

    private String name(String[] params) {
        return params[0];
    }

    private Set<String> skills(String[] params) {
        return Arrays.stream(params)
                .filter(s -> s.startsWith("--skills"))
                .findFirst()
                .map(s -> s.replace("--skills=", "").split(","))
                .map(arr -> Arrays.stream(arr).collect(Collectors.toSet()))
                .orElseThrow();
    }

    private int experience(String[] params) {
        return Arrays.stream(params)
                .filter(s -> s.startsWith("--exp"))
                .findFirst()
                .map(s -> s.replace("--exp=", ""))
                .map(Integer::parseInt)
                .orElseThrow();
    }
}
