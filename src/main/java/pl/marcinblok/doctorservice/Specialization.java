package pl.marcinblok.doctorservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true)
    private String name;


    @ManyToMany
    @JoinTable(name = "doctor_specialization", joinColumns = @JoinColumn(name = "doctor_id"), inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private Set<Doctor> doctors;

    public Specialization() {
    }


    public Specialization(String name) {
        this.name = name;
    }

}
