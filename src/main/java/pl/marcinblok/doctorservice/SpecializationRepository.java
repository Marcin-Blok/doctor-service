package pl.marcinblok.doctorservice;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository przyjmuje encję (Specialization) oraz id (Integer)

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    Specialization getSpecializationByName(String name);
}
