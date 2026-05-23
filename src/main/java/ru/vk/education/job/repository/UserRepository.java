package ru.vk.education.job.repository;

import ru.vk.education.job.domain.User;

import java.util.List;

public interface UserRepository {

    List<User> findUsers();

    User findUserById(Long id);

    User findUserByName(String name);

    void saveUser(User user);
}
