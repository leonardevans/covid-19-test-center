package com.example.covid19testcenter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean available = false;
    private int patients = 0;
    private String name;
    private String description;

    public Vaccine(boolean available, int patients, String name, String description) {
        this.available = available;
        this.patients = patients;
        this.name = name;
        this.description = description;
    }

    public Vaccine(String name) {
        this.name = name;
    }
}
