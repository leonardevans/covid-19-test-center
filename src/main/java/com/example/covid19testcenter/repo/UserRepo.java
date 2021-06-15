package com.example.covid19testcenter.repo;

import com.example.covid19testcenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM user WHERE first_name LIKE %?1% OR last_name LIKE %?1% OR username LIKE %?1% ORDER BY first_name" , nativeQuery = true)
    List<User> findAllByFirstNameContainingOrLastNameContainingOrUsernameContaining(@Param("search") String search);
}
