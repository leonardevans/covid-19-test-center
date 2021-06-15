package com.example.covid19testcenter;

import com.example.covid19testcenter.model.User;
import com.example.covid19testcenter.model.Vaccine;
import com.example.covid19testcenter.repo.UserRepo;
import com.example.covid19testcenter.repo.VaccineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Covid19TestCenterApplication implements CommandLineRunner {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    VaccineRepo vaccineRepo;

    public static void main(String[] args) {
        SpringApplication.run(Covid19TestCenterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        if (!userRepo.existsByUsername("admin"))
        {
            //create a user with admin role
            User admin = new User();
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setUsername("admin");
            admin.setRole("ROLE_ADMIN");
            admin.setPassword(passwordEncoder.encode("admin"));

            //save the user
            userRepo.save(admin);
        }

        if (!userRepo.existsByUsername("secretary"))
        {
            //create a user with secretary role
            User secretary = new User();
            secretary.setFirstName("secretary");
            secretary.setLastName("secretary");
            secretary.setUsername("secretary");
            secretary.setRole("ROLE_SECRETARY");
            secretary.setPassword(passwordEncoder.encode("secretary"));

            //save the user
            userRepo.save(secretary);
        }

        if (!userRepo.existsByUsername("user"))
        {
            //create a user with user role
            User user = new User();
            user.setFirstName("user");
            user.setLastName("user");
            user.setUsername("user");
            user.setRole("ROLE_USER");
            user.setPassword(passwordEncoder.encode("user"));

            //save the user
            userRepo.save(user);
        }

        //check id vaccine exists
        if (!vaccineRepo.existsByName("COVID19VACCINE"))
        {
            //create vaccine
            Vaccine vaccine = new Vaccine(false, 0, "COVID19VACCINE", "COVID19VACCINE vaccine");


            //save the vaccine
            vaccineRepo.save(vaccine);
        }
    }
}
