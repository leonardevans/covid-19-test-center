package com.example.covid19testcenter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String location;

    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();

    public User(String firstName, String lastName, String username, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
