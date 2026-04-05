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

public class Main {
    public static void main(String[] args) {
        ServiceLink serviceLink = new ServiceLink();
        MatchOfUserToJobService matchOfUserToJobService = serviceLink.getMatchOfUserToJobService();
        JobService jobService = serviceLink.getJobService();
        UserService userService = serviceLink.getUserService();
        FileService fileService = new FileService("log.txt");

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

    }
}

//        // Выполнение команд из файла при старте приложения
//        try (BufferedReader reader = fileService.getAllCommandsFromFile()) {
//            while (reader.ready()) {
//                String[] inputCommand = reader.readLine().split(" ");
//                String command = inputCommand[0];
//                if (command.equals("user") || command.equals("job"))
//                    executionCommand(inputCommand, command, userService, jobService, matchOfUserToJobService, fileService);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Выполнение последовательно команд из консоли пока не будет введено exit
//        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
//            while (true) {
//                String[] inputLine = bufferedReader.readLine().trim().split(" ");
//                String command = inputLine[0];
//                executionCommand(inputLine, command, userService, jobService, matchOfUserToJobService, fileService);
//                if (command.equals("exit")) break;
//                fileService.saveCommandInFile(inputLine);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void executionCommand(String[] inputLine, String command, UserService userService, JobService jobService, MatchOfUserToJobService matchOfUserToJobService, FileService fileService) throws IOException {
//        switch (command) {
//            case "user":
//                User user = parsingAddUser(inputLine);
//                if (user != null)
//                    userService.addUser(user);
//                break;
//            case "user-list":
//                System.out.println(userService);
//                break;
//            case "job":
//                Job job = parsingAddJob(inputLine);
//                if (job != null)
//                    jobService.addJob(job);
//                break;
//            case "job-list":
//                System.out.println(jobService);
//                break;
//            case "suggest":
//                User u = userService.getUserByName(inputLine[1]);
//                if (u != null) {
//                    List<Job> jobs = matchOfUserToJobService.getTwoJobForUser(u);
//                    for (Job j : jobs)
//                        System.out.println(j);
//                }
//                break;
//            case "history":
//                System.out.println(fileService.getAllCommandsFromFile().lines().collect(Collectors.joining("\n")));
//                break;
//        }
//    }
//
//    private static User parsingAddUser(String[] inputLine) {
//        String name;
//        int experience;
//        if (inputLine.length < 2) return null;
//        name = inputLine[1];
//
//        List<String[]> params = new ArrayList<>();
//        params.add(splitParam(inputLine[2]));
//        params.add(splitParam(inputLine[3]));
//        String[] param1 = searchParam(params, "--skills");
//        if (param1 == null) return null;
//        String[] param2 = searchParam(params, "--exp");
//        if (param2 == null) return null;
//        String[] splitSkills = param1[1].split(",");
//        Set<String> skills = new TreeSet<>(List.of(splitSkills));
//        experience = Integer.parseInt(param2[1]);
//
//        return new User(name, skills, experience);
//    }
//
//    private static Job parsingAddJob(String[] inputLine) {
//        String name;
//
//        if (inputLine.length < 2) return null;
//        name = inputLine[1];
//
//        List<String[]> params = new ArrayList<>();
//        params.add(splitParam(inputLine[2]));
//        params.add(splitParam(inputLine[3]));
//        params.add(splitParam(inputLine[4]));
//        String[] param1 = searchParam(params, "--company");
//        if (param1 == null) return null;
//        String[] param2 = searchParam(params, "--tags");
//        if (param2 == null) return null;
//        String[] param3 = searchParam(params, "--exp");
//        if (param3 == null) return null;
//
//        String company = param1[1];
//        String[] splitTags = param2[1].split(",");
//        Set<String> tags = new TreeSet<>(List.of(splitTags));
//        int experience = Integer.parseInt(param3[1]);
//        return new Job(name, company, tags, experience);
//    }
//
//    private static String[] searchParam(List<String[]> params, String nameParam) {
//        return params.stream()
//                .filter(param -> param.length > 0 && param[0].equals(nameParam))
//                .findFirst()
//                .orElse(null);
//    }
//
//    private static String[] splitParam(String param) {
//        if (param.isEmpty()) return null;
//        return param.split("=");
//    }
//}