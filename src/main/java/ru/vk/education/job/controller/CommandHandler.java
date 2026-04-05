package ru.vk.education.job.controller;

import ru.vk.education.job.command.Command;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandHandler implements Handler {

    private final Map<String, Command> commands;

    public CommandHandler(List<Command> commands) {
        this.commands = commands.stream()
                .collect(Collectors.toMap(Command::name, command -> {
                    return command;
                }));
    }

    @Override
    public void handler(InputCommand inputCommand) {
        commands.get(inputCommand.command()).execute(inputCommand.params());
    }
}
