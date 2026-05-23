package ru.vk.education.job.domain;

import java.util.Set;

public class Job {
    private Long id;
    private final String name;
    private final String company;
    private final Set<String> tags;
    private final Integer experience;

    public Job(Long id, String name, String company, Set<String> tags, Integer experience) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.tags = tags;
        this.experience = experience;
    }

    public Job(String name, String company, Set<String> tags, Integer experience) {
        this(0L, name, company, tags, experience);
    }

    public String name() {
        return name;
    }

    public String company() {
        return company;
    }

    public Integer experience() {
        return experience;
    }

    public Set<String> tags() {
        return tags;
    }

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}