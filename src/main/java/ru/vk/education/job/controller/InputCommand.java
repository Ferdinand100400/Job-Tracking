package ru.vk.education.job.controller;

import java.util.Arrays;

public class InputCommand {

    private final String[] commandParts;

    public InputCommand(String command) {
        this.commandParts = command.split(" ");
    }

    public String command() {
        return commandParts[0];
    }

    public String[] params() {
        return Arrays.copyOfRange(commandParts, 1, commandParts.length);
    }
}
