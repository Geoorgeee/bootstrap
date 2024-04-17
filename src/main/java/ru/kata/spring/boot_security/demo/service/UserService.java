package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);

    Optional<User> getUser(long id);

    List<User> getAllUsers();

    void updateUser(User user);

    void removeUser(long id);

    User findByUsername(String name);
    public Optional<User> findById(long id);
}
