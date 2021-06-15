package com.example.covid19testcenter.repo;

import com.example.covid19testcenter.model.Booking;
import com.example.covid19testcenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    boolean existsByVaccineDateAndTime(Date date, Time time);

    List<Booking> findByCompleted(boolean completed);

    List<Booking> findByUser(User user);

    List<Booking> findByCompletedAndUser(boolean completed, User user);
}
