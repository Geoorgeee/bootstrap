package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getAllUsers(Model model, Principal principal) {
        User user1 = userService.findByUsername(principal.getName());
        User user = new User();
        model.addAttribute("userMain",user1);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
        return "users";
    }

    @PostMapping("/userCreate")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/updateUser")
        public String addUser(HttpServletRequest request, @RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "role" , required = false) List<String> role) {
            User userToUpdate = userService.findById(userId).get();
            Set<Role> roles= new HashSet<>();
            String firstName = request.getParameter("first-name");
            String lastName = request.getParameter("last-name");
            int age = Integer.parseInt(request.getParameter("age"));
            String password = request.getParameter("password");
            if(role == null){

            }

            else if(role.size()==2){
                roles.add(roleRepository.getById(1L));
                roles.add(roleRepository.getById(2L));
                userToUpdate.setRoles(roles);
            }
            else if(role.get(0).equals("1")){
                roles.add(roleRepository.getById(1L));
                userToUpdate.setRoles(roles);
            }
            else if(role.get(0).equals("2")){
                roles.add(roleRepository.getById(2L));
                userToUpdate.setRoles(roles);
            }
            userToUpdate.setUsername(firstName);
            userToUpdate.setLastName(lastName);
            userToUpdate.setAge((byte) age);


            if(!password.isEmpty()){
                userToUpdate.setPassword(password);
            }


            userService.updateUser(userToUpdate);
            return "redirect:/admin";
    }
    @GetMapping("/userCreate")
    public String create(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "saveUser";
    }

    @PostMapping("/removeUser")
    public String removeUser(@RequestParam(value = "userId", required = false) Integer userId) {
        userService.removeUser(userId);
        return "redirect:/admin";
    }
    @GetMapping("/updateUser")
    public String getEditUserForm(Model model, @RequestParam("id") long id) {
        // User usermain = new User();
        model.addAttribute("user", userService.getUser(id));
        //model.addAttribute("userMain",usermain);
        return "newUser";
    }

    @GetMapping("/new")
    public String createUserForm(@ModelAttribute("user") User user, Model model) {
        User user1 = new User();
        model.addAttribute("user",user1);
        return "saveUser";
    }


}
