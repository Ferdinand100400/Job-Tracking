package ru.vk.education.job.command;

import java.util.List;

public interface Command {
    String name();

    void execute(String[] params);
}
