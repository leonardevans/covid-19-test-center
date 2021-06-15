package com.example.covid19testcenter.controller;

import com.example.covid19testcenter.model.User;
import com.example.covid19testcenter.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public String showUsers(Model model)
    {
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    @PostMapping("/search_user")
    public String searchUsers(Model model, @RequestParam("search") String search)
    {
        model.addAttribute("users", userRepo.findAllByFirstNameContainingOrLastNameContainingOrUsernameContaining(search));
        return "users";
    }

    @GetMapping("/addUser")
    public String showAddUser(Model model)
    {
        model.addAttribute("errorMessage", null);
        model.addAttribute("user", new User());
        return "add_user";
    }

    @PostMapping("/addUser")
    public String addUser(Model model, @ModelAttribute("user") User user)
    {
        //check if username is taken
        if (!userRepo.existsByUsername(user.getUsername())){
            user.setPassword(passwordEncoder.encode(user.getUsername()));

            //save user
            userRepo.save(user);

            return "redirect:/addUser?added";
        }else
        {
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", "A user with this name exists. Please user another username");
            return "add_user";
        }
    }
}
