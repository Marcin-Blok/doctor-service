package pl.marcinblok.doctorservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String pesel;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Day> days = new ArrayList<>();
    @ManyToMany(mappedBy = "doctors",  cascade = CascadeType.PERSIST)
    private Set<Specialization> specializations;


    public Doctor() {
    }

    public Doctor(String name, String surname,String pesel){
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
    }
    public void addDay(Day day ) {
        day.setDoctor(this);
        days.add(day);
    }
}
