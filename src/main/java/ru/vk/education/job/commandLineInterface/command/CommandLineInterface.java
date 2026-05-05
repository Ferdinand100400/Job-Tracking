package ru.vk.education.job.commandLineInterface.command;

import org.springframework.stereotype.Component;
import ru.vk.education.job.commandLineInterface.command.controller.CommandHandler;
import ru.vk.education.job.commandLineInterface.command.controller.InputCommand;
import ru.vk.education.job.commandLineInterface.command.controller.LoggedCommandHandler;
import ru.vk.education.job.service.FileService;
import ru.vk.education.job.service.SchedulerService;
import ru.vk.education.job.service.ServiceLink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class CommandLineInterface {

    private final ServiceLink serviceLink;
    private final FileService fileService;

    public CommandLineInterface(ServiceLink serviceLink, FileService fileService) {
        this.serviceLink = serviceLink;
        this.fileService = fileService;
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        SchedulerService task = new SchedulerService(serviceLink);
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);

        CommandHandler commandHandler = new CommandHandler(List.of(
                new AddUserCmd(serviceLink.getUserService()),
                new UserListCmd(serviceLink.getUserService()),
                new AddJobCmd(serviceLink.getJobService()),
                new JobListCmd(serviceLink.getJobService()),
                new SuggestCmd(serviceLink.getUserService(), serviceLink.getMatchOfUserToJobService()),
                new HistoryCmd(fileService),
                new StatisticsCmd(serviceLink.getJobService(), serviceLink.getUserService(), serviceLink.getMatchOfUserToJobService())
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
                if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.out.println("Ошибка закрытия потока scheduler");
                }
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();

            //    }));
        }
    }
}
