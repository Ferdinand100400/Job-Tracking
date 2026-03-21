package ru.vk.education.job;

import ru.vk.education.job.domain.Job;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.service.JobService;
import ru.vk.education.job.service.MatchOfUserToJobService;
import ru.vk.education.job.service.ServiceLink;
import ru.vk.education.job.service.UserService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServiceLink serviceLink = new ServiceLink();
        MatchOfUserToJobService matchOfUserToJobService = serviceLink.getMatchOfUserToJobService();
        JobService jobService = serviceLink.getJobService();
        UserService userService = serviceLink.getUserService();

        while (true) {
            if (scanner.hasNext()) {
                String command = scanner.next();
                switch (command) {
                    case "user":
                        User user = parsingAddUser(scanner);
                        if (user != null)
                            userService.addUser(user);
                        break;
                    case "user-list":
                        System.out.println(userService);
                        break;
                    case "job":
                        Job job = parsingAddJob(scanner);
                        if (job != null)
                            jobService.addJob(job);
                        break;
                    case "job-list":
                        System.out.println(jobService);
                        break;
                    case "suggest":
                        if (scanner.hasNext()) {
                            User u = userService.getUserByName(scanner.next());
                            if (u != null) {
                                List<Job> jobs = matchOfUserToJobService.getTwoJobForUser(u);
                                for (Job j : jobs)
                                    System.out.println(j);
                            }
                        }
                        break;
                }
                if (command.equals("exit")) break;
            }
        }
        scanner.close();
    }

    private static User parsingAddUser(Scanner scanner) {
        String name;
        int experience;
        if (!scanner.hasNext()) return null;
        name = scanner.next();

        List<String[]> params = new ArrayList<>();
        params.add(splitParam(scanner));
        params.add(splitParam(scanner));
        String[] param1 = searchParam(params, "--skills");
        if (param1 == null) return null;
        String[] param2 = searchParam(params, "--exp");
        if (param2 == null) return null;
        String[] splitSkills = param1[1].split(",");
        Set<String> skills = new TreeSet<>(List.of(splitSkills));
        experience = Integer.parseInt(param2[1]);

        return new User(name, skills, experience);
    }

    private static Job parsingAddJob(Scanner scanner) {
        String name;

        if (!scanner.hasNext()) return null;
        name = scanner.next();

        List<String[]> params = new ArrayList<>();
        params.add(splitParam(scanner));
        params.add(splitParam(scanner));
        params.add(splitParam(scanner));
        String[] param1 = searchParam(params, "--company");
        if (param1 == null) return null;
        String[] param2 = searchParam(params, "--tags");
        if (param2 == null) return null;
        String[] param3 = searchParam(params, "--exp");
        if (param3 == null) return null;

        String company = param1[1];
        String[] splitTags = param2[1].split(",");
        Set<String> tags = new TreeSet<>(List.of(splitTags));
        int experience = Integer.parseInt(param3[1]);
        return new Job(name, company, tags, experience);
    }

    private static String[] searchParam(List<String[]> params, String nameParam) {
        return params.stream()
                .filter(param -> param.length > 0 && param[0].equals(nameParam))
                .findFirst()
                .orElse(null);
    }

    private static String[] splitParam(Scanner scanner) {
        if (!scanner.hasNext()) return null;
        String param = scanner.next();
        return param.split("=");
    }
}