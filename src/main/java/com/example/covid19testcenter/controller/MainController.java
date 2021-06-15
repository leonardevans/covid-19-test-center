package com.example.covid19testcenter.controller;

import com.example.covid19testcenter.model.Booking;
import com.example.covid19testcenter.model.User;
import com.example.covid19testcenter.repo.BookingRepo;
import com.example.covid19testcenter.repo.TestRepo;
import com.example.covid19testcenter.repo.UserRepo;
import com.example.covid19testcenter.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLogin()
    {
        return "login";
    }

    @GetMapping("/")
    public String showHome(Model model)
    {
        //get logged in user
        User user = securityUtil.getLoggedInUser();

        List<Booking> bookings;

        if (user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_SECRETARY"))
        {
            model.addAttribute("bookings", bookingRepo.findAll());
        }
        else
        {
            model.addAttribute("bookings", bookingRepo.findByUser(user));
        }
        return "index";
    }

    @GetMapping("/signUp")
    public String showSignUp(Model model)
    {
        model.addAttribute("errorMessage", null);
        model.addAttribute("user", new User());
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(Model model, @ModelAttribute("user") User user)
    {
        //check if username is taken
        if (userRepo.existsByUsername(user.getUsername())){
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", "Username is taken. Please user another username.");
            return "signUp";
        }

        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set role
        user.setRole("ROLE_USER");

        //save the user
        userRepo.save(user);

        return "redirect:/signUp?created";
    }
}
