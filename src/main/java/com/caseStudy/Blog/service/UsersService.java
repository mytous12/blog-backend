package com.caseStudy.Blog.service;

import com.caseStudy.Blog.repository.UsersRepository;
import com.caseStudy.Blog.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean addUser(Users user) {
        if (usersRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        if (user.getRole() == null) {
            user.setRole("user");
        }
        user.setActive(1);
        user.setSubscribers(0L);
        usersRepository.saveAndFlush(user);
        return true;
    }

    public String login(Principal principal) {
        Users users = usersRepository.findByEmail(principal.getName()).get();
        return "\"" + users.getRole() + "\"";
    }

    public Users getUser(Principal principal) {
        return usersRepository.findByEmail(principal.getName()).get();
    }

    public Users updateUser(Users newUser, Principal principal) {
        Users oldUser = usersRepository.findByEmail(principal.getName()).get();
        newUser.setId(oldUser.getId());
        newUser.setRole(oldUser.getRole());
        newUser.setActive(oldUser.getActive());
        newUser.setSubscribers(oldUser.getSubscribers());
        usersRepository.saveAndFlush(newUser);
        return newUser;
    }

    public List<Users> editUser(Users newUser, Long id) {
        Users oldUser = usersRepository.findById(id).get();
        newUser.setId(oldUser.getId());
        newUser.setRole(oldUser.getRole());
        newUser.setActive(oldUser.getActive());
        newUser.setSubscribers(oldUser.getSubscribers());
        usersRepository.saveAndFlush(newUser);
        return usersRepository.findAll();
    }

    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    public Users getUserById(Long id) {
        return usersRepository.findById(id).get();
    }

    public List<Users> deleteAllUsers() {
        usersRepository.deleteAll();
        return usersRepository.findAll();
    }

    public List<Users> deleteUser(Long id) {
        usersRepository.deleteById(id);
        return usersRepository.findAll();
    }

    public Boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            request.getSession().invalidate();
        }
        return true;
    }

    public List<Users> toggleActivate(Long id) {
        Users user = usersRepository.findById(id).get();
        if (user.getActive() == 1) {
            user.setActive(0);
        } else {
            user.setActive(1);
        }
        usersRepository.saveAndFlush(user);
        return usersRepository.findAll();
    }

    public String getName(Principal principal) {
        Users users = usersRepository.findByEmail(principal.getName()).get();
        return "\"" + users.getName().split(" ")[0] + "\"";
    }
}
