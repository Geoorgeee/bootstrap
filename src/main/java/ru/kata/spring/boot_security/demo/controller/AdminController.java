package ru.kata.spring.boot_security.demo.controller;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping
    public List<User> indexView() {
        return userService.getAllUsers();
    }

    @PostMapping("/addUser")
    public ResponseEntity<HttpStatus> addUserView(@RequestBody @Valid User user, BindingResult bindingResult) {
        userService.addUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/addOrUpdate/{id}")
    public ResponseEntity<HttpStatus> add(@PathVariable("id") Long id, @RequestBody @Valid User user, BindingResult bindingResult) {
        String pas = getUserById(id).getPassword();
        user.setId(id);
        userService.updateUser(user);
        if(user.getPassword().isEmpty()){
            user.setPassword(pas);
            user.setId(id);
            userService.updateUser(user);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        else{
            userService.addUser(user);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/removeUser/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.findById(id).get();
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }



}
