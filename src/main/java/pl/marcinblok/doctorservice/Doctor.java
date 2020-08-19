package pl.marcinblok.doctorservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public Doctor() {
    }

    public Doctor(String name, String surname, String specialization,String pesel){
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.pesel = pesel;
    }
}
