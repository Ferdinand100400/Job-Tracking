package ru.vk.education.job.commandLineInterface.command;

import ru.vk.education.job.service.FileService;

import java.util.stream.Collectors;

public class HistoryCmd implements Command {

    FileService fileService;

    public HistoryCmd(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public String name() {
        return "history";
    }

    @Override
    public void execute(String[] params) {
        System.out.println(fileService.getAllCommandsFromFile().lines().collect(Collectors.joining("\n")));
    }
}
