package ru.vk.education.job.domain;

import java.util.Set;


public class User {
    private Long id;
    private final String name;
    private final Set<String> skills;
    private final Integer experience;


    public User(Long id, String name, Set<String> skills, Integer experience) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.experience = experience;
    }

    public User(String name, Set<String> skills, Integer experience) {
        this(0L, name, skills, experience);
    }

    public String name() {
        return name;
    }

    public Set<String> skills() {
        return skills;
    }

    public Integer experience() {
        return experience;
    }

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}