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
    @JoinColumn(name="doctor_id", nullable=true)
    private Doctor doctor;

    public Day(){}

    public Day(DayOfWeek day){
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
