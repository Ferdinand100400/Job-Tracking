package ru.vk.education.job.repository.mapping;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class GeneralMapper {

    public Set<String> stringToSet(String skills) {
        return Arrays.stream(skills.split(","))
                .map(String::strip)
                .collect(Collectors.toSet());
    }
}
