package com.caseStudy.Blog.controller;

import com.caseStudy.Blog.service.UsersService;
import com.caseStudy.Blog.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private UsersService usersService;

    @Autowired
    public AdminController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/add-user")
    public Boolean addUser(@RequestBody Users users) {
        return usersService.addUser(users);
    }

    @DeleteMapping("/remove-user")
    public List<Users> removeUser(@RequestParam("id") Long id) {
        return usersService.deleteUser(id);
    }

    @PutMapping("/edit-user")
    public List<Users> editUser(@RequestBody Users users, @RequestParam("id") Long id) {
        return usersService.editUser(users, id);
    }

    @GetMapping("/get-user")
    public Users getUserById(@RequestParam("id") Long id) {
        return usersService.getUserById(id);
    }

    @GetMapping("/get-users")
    public List<Users> getUsers() {
        return usersService.getUsers();
    }

    @PostMapping("/activate")
    public List<Users> toggleActivate(@RequestParam("id") Long id) {
        return usersService.toggleActivate(id);
    }
}
