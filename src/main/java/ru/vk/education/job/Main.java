package ru.vk.education.job;

import ru.vk.education.job.command.*;
import ru.vk.education.job.controller.CommandHandler;
import ru.vk.education.job.controller.InputCommand;
import ru.vk.education.job.controller.LoggedCommandHandler;
import ru.vk.education.job.service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ServiceLink serviceLink = new ServiceLink();
        MatchOfUserToJobService matchOfUserToJobService = serviceLink.getMatchOfUserToJobService();
        JobService jobService = serviceLink.getJobService();
        UserService userService = serviceLink.getUserService();
        FileService fileService = new FileService("log.txt");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        SchedulerService task = new SchedulerService(userService, matchOfUserToJobService);
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);

        CommandHandler commandHandler = new CommandHandler(List.of(
                new AddUserCmd(userService),
                new UserListCmd(userService),
                new AddJobCmd(jobService),
                new JobListCmd(jobService),
                new SuggestCmd(userService, matchOfUserToJobService),
                new HistoryCmd(fileService),
                new StatisticsCmd(jobService, userService, matchOfUserToJobService)
        ));

        LoggedCommandHandler loggedCommandHandler = new LoggedCommandHandler(commandHandler, fileService);

        // Выполнение команд из файла при старте приложения
        try (BufferedReader reader = fileService.getAllCommandsFromFile()) {
            while (reader.ready()) {
                InputCommand inputCommand = new InputCommand(reader.readLine().trim());
                if (inputCommand.command().equals("user") || inputCommand.command().equals("job"))
                    commandHandler.handler(inputCommand);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Выполнение последовательно команд из консоли пока не будет введено exit
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                InputCommand inputCommand = new InputCommand(bufferedReader.readLine().trim());
                if (inputCommand.command().equals("exit")) break;
                if (inputCommand.command().isEmpty()) continue;
                loggedCommandHandler.handler(inputCommand);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Закрытие потока
        //   Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.MINUTES)) {
                scheduler.shutdownNow();
                scheduler.awaitTermination(10, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();

            //    }));

        }
    }
}