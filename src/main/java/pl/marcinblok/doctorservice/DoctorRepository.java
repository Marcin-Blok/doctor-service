package pl.marcinblok.doctorservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Doctor getDoctorByPesel(String pesel);
}
