package com.example.covid19testcenter.repo;

import com.example.covid19testcenter.model.Booking;
import com.example.covid19testcenter.model.Test;
import com.example.covid19testcenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {
    List<Test> findByStatusIsNotNull();
    List<Test> findByStatusIsNotNullAndBooking_User(User user);
}
