package pl.marcinblok.doctorservice;

import javax.persistence.*;

@Entity
public class Day {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id", nullable=false, insertable=false, updatable=false)
    private Doctor doctor;

    public Day(){}

    public Day(DayOfWeek day){
        this.day = day;
    }
}
