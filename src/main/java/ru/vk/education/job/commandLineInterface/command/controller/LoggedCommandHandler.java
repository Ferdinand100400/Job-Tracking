package ru.vk.education.job.commandLineInterface.command.controller;

import ru.vk.education.job.service.FileService;

public class LoggedCommandHandler implements Handler {

    private final Handler delegate;
    private final FileService fileService;

    public LoggedCommandHandler(Handler delegate, FileService fileService) {
        this.delegate = delegate;
        this.fileService = fileService;
    }

    @Override
    public void handler(InputCommand inputCommand) {
        delegate.handler(inputCommand);
        fileService.saveCommandInFile(inputCommand);
    }
}
