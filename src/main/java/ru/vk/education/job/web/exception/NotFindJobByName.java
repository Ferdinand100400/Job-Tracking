package ru.vk.education.job.web.exception;

public class NotFindJobByName extends RuntimeException {

    private final String name;

    public NotFindJobByName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
