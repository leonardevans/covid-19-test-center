package com.example.covid19testcenter.security;

import com.example.covid19testcenter.model.User;
import com.example.covid19testcenter.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUtil {
    @Autowired
    private UserRepo userRepo;

    //get logged in user from security context
    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        UserDetails loggedInUser;
        try {
            loggedInUser = (UserDetails) principal;
        }
        catch (Exception e){
            return null;
        }

        Optional<User> optionalUser = userRepo.findByUsername(loggedInUser.getUsername());
        return optionalUser.orElse(null);
    }
}
