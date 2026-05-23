package ru.vk.education.job.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.mapper.UserMapper;
import ru.vk.education.job.repository.UserRepository;
import ru.vk.education.job.web.dto.UserDto;
import ru.vk.education.job.web.exception.NotFindUserByName;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final ServiceLink serviceLink;

    public UserService(UserRepository userRepo, ServiceLink serviceLink) {
        this.userRepo = userRepo;
        this.serviceLink = serviceLink;
    }

    public void addUser(UserDto userDto) {
        if (userDto == null) return;
        try {
            getUserByName(userDto.name());
        } catch (NotFindUserByName e) {
            userRepo.saveUser(UserMapper.dtoToDomain(userDto));
            serviceLink.getMatchOfUserToJobService().addMatch(userDto, serviceLink.getListJobs());
        }
    }

    public List<UserDto> getListUsers() {
        return UserMapper.domainListToDtoList(userRepo.findUsers());
    }

    public UserDto getUserByName(String name) {
        try {
            return UserMapper.domainToDto(userRepo.findUserByName(name));
        } catch (EmptyResultDataAccessException e1) {
            throw new NotFindUserByName(name);
        } catch (IncorrectResultSizeDataAccessException e2) {
            System.out.println("Ошибка: не может быть в системе пользователей с одинаковым именем");
            e2.printStackTrace();
        }
        return null;
    }

    // Топ N скиллов среди всех пользователей (скиллы, которые чаще всего встречаются)
    public List<String> topNSkills(int n) {
        return getListUsers().stream()
                .flatMap(user -> user.skills().stream())
                .collect(Collectors.groupingBy(skill -> skill, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
       String res = "";
       List<UserDto> users = getListUsers();
       for (int i = 0; i < users.size(); i++) {
           res = res.concat(users.get(i).toString());
           if (i != users.size() - 1) res = res.concat("\n");
       }
       return res;
    }
}