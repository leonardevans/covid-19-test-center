package com.example.covid19testcenter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date bookDate;
    private Date vaccineDate;
    private Time time;
    private boolean completed;

    @ManyToOne
    private User user;

    @OneToOne( cascade = CascadeType.ALL)
    private Test test = new Test();

    public Booking(Date bookDate, Date vaccineDate, Time time, boolean completed, User user) {
        this.bookDate = bookDate;
        this.vaccineDate = vaccineDate;
        this.time = time;
        this.completed = completed;
        this.user = user;
    }
}
