package pl.marcinblok.doctorservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String specialization;
    private String pesel;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Day> days = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(String name, String surname, String specialization,String pesel){
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.pesel = pesel;
    }
}
