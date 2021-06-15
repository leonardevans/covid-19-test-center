package com.example.covid19testcenter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String status ;
    private String details = null;
    private boolean vaccinated = false;

    @OneToOne(mappedBy = "test")
    private Booking booking;

    public Test(String status, String details, Booking booking) {
        this.status = status;
        this.details = details;
        this.booking = booking;
    }

    public Test(String status, String details) {
        this.status = status;
        this.details = details;
    }
}
