package com.example.covid19testcenter.repo;

import com.example.covid19testcenter.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {
    Optional<Vaccine> findByName(String name);
    boolean existsByName(String name);
}
