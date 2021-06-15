package com.example.covid19testcenter.controller;

import com.example.covid19testcenter.model.Booking;
import com.example.covid19testcenter.model.Test;
import com.example.covid19testcenter.model.User;
import com.example.covid19testcenter.model.Vaccine;
import com.example.covid19testcenter.repo.BookingRepo;
import com.example.covid19testcenter.repo.TestRepo;
import com.example.covid19testcenter.repo.UserRepo;
import com.example.covid19testcenter.repo.VaccineRepo;
import com.example.covid19testcenter.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.Optional;

@Controller
public class TestController {
    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    TestRepo testRepo;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    VaccineRepo vaccineRepo;

    @GetMapping("/tests")
    public String showTests(Model model)
    {
        //get logged in user
        User user = securityUtil.getLoggedInUser();

        if (user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_SECRETARY"))
        {
            model.addAttribute("bookings", bookingRepo.findByCompleted(true));
        }
        else
        {
            model.addAttribute("bookings", bookingRepo.findByCompletedAndUser(true, user));
        }

        return "tests";
    }

    @GetMapping("/editTest/{bookingId}")
    public String showEditBookingTest(@PathVariable("bookingId") long bookingId, Model model)
    {
        model.addAttribute("errorMessage", null);
        model.addAttribute("test", bookingRepo.findById(bookingId).get().getTest());
        return "editTest";
    }

    @PostMapping("/updateTest")
    public String updateTest(Model model, @ModelAttribute("text") Test test)
    {
        Optional<Test> optionalTest = testRepo.findById(test.getId());

        //get the test
        Test testToUpdate = optionalTest.get();

        //if the user is to be vaccinated check for vaccine availability
        if (test.isVaccinated()){
            //get the vaccine
            Vaccine vaccine = vaccineRepo.findByName("COVID19VACCINE").orElse(new Vaccine("COVID19VACCINE"));

            //check vaccine availability
            if (!vaccine.isAvailable()){
                model.addAttribute("errorMessage", "The vaccine currently is not available");
                model.addAttribute("test", test);
                return "editTest";
            }

            //update the vaccine
            vaccine.setPatients(vaccine.getPatients() -1);

            if (vaccine.getPatients() <=0 ){
                vaccine.setAvailable(false);
            }

            //save the vaccine
            vaccineRepo.save(vaccine);
        }

        testToUpdate.setDetails(test.getDetails());
        testToUpdate.setStatus(test.getStatus());
        testToUpdate.setVaccinated(test.isVaccinated());

        Booking booking = testToUpdate.getBooking();
        booking.setCompleted(true);

        booking = bookingRepo.save(booking);
        testRepo.save(testToUpdate);

        return "redirect:/viewBooking/" + booking.getId() + "?testUpdated";
    }
}
