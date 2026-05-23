package ru.vk.education.job.web.exception;

public class NotFindUserByName extends RuntimeException {
    private final String name;

    public NotFindUserByName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
