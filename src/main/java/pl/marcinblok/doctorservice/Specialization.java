package pl.marcinblok.doctorservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
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
    @JoinTable(name = "doctor_specialization", joinColumns = @JoinColumn(name = "specialization_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    @JsonIgnore
    private Set<Doctor> doctors = new HashSet<>();

    public Specialization() {
    }


    public Specialization(String name) {
        this.name = name;
    }

}
