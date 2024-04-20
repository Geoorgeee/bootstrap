package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final UserServiceImpl userService;
    private final RoleRepository roleRepository;

    @Autowired
    public Init(UserServiceImpl userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @PostConstruct
    void kak(){
        Role rol1 = new Role("ROLE_USER");
        Role rol2 = new Role("ROLE_ADMIN");
        roleRepository.save(rol1);
        roleRepository.save(rol2);
        User user1 = new User();
        user1.setPassword("100");
        user1.setRoles(Collections.singleton(rol2));
        user1.setAge((byte) 10);
        user1.setUsername("goga");
        user1.setLastname("goga");
        User user2 = new User();
        user2.setPassword("100");
        user2.setRoles( Collections.singleton(rol1));
        user2.setAge((byte) 10);
        user2.setUsername("gowa");
        user2.setLastname("gowa");
        userService.addUser(user1);
        userService.addUser(user2);
    }
}