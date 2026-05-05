package ru.vk.education.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.vk.education.job.commandLineInterface.command.*;
import ru.vk.education.job.service.*;

import java.util.*;
import java.util.concurrent.*;

@SpringBootApplication
@EnableScheduling
public class Main {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        CommandLineInterface commandLineInterface = context.getBean(CommandLineInterface.class);
        commandLineInterface.start();
    }
}