package com.example.covid19testcenter.controller;

import com.example.covid19testcenter.model.Booking;
import com.example.covid19testcenter.model.User;
import com.example.covid19testcenter.repo.BookingRepo;
import com.example.covid19testcenter.repo.TestRepo;
import com.example.covid19testcenter.repo.UserRepo;
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
public class BookingController {
    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    TestRepo testRepo;

    @Autowired
    SecurityUtil securityUtil;

    @GetMapping("/book")
    public String showBookingPage(Model model)
    {
        model.addAttribute("errorMessage", null);
        model.addAttribute("booking", new Booking());
        return "booking";
    }

    @PostMapping("/book")
    public String bookTest(Model model, @ModelAttribute("booking") Booking booking)
    {
        //get logged in user
        User user = securityUtil.getLoggedInUser();

        //get current date
        long now = System.currentTimeMillis();
        Date date = new java.sql.Date(now);

        //check if there's a booking at that time
        if (!bookingRepo.existsByVaccineDateAndTime(booking.getVaccineDate(), booking.getTime()))
        {
            booking.setBookDate(date);
            booking.setCompleted(false);
            booking.setUser(user);
            bookingRepo.save(booking);
            return "redirect:/book?booked";
        }else
        {
            model.addAttribute("errorMessage", "Someone has booked at that time. please select another time or date");
            model.addAttribute("booking", booking);
            return "booking";
        }
    }

    @GetMapping("/viewBooking/{id}")
    public String viewBooking(@PathVariable("id") long bookingId, Model model)
    {
        model.addAttribute("booking", bookingRepo.findById(bookingId).get());
        return "bookDetails";
    }

    @GetMapping("/userBookings/{userId}")
    public String viewUserBookings(@PathVariable("userId") long userId, Model model)
    {
        Optional<User> user = userRepo.findById(userId);
        model.addAttribute("bookings", bookingRepo.findByUser(user.get()));
        return "index";
    }
}
