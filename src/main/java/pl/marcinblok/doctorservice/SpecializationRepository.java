package pl.marcinblok.doctorservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
}
