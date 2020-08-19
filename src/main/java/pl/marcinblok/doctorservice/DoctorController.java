package pl.marcinblok.doctorservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class DoctorController {

    Pattern pattern = Pattern.compile("[0-9],{11}");

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping(path = "/doctors")
    public @ResponseBody
    ResponseEntity<String>add(@RequestBody Doctor doctor){
        String pesel = doctor.getPesel();
        Matcher matcher = pattern.matcher(pesel);
        if (doctor.getName() != null && doctor.getSurname() != null && doctor.getSpecialization() != null) {
            return peselValidator(doctor, matcher);
        } else {
            return new ResponseEntity<>("Nie zapisano, następujące pola muszą zostać wypełnione: Imię, Nazwisko, Pesel", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private ResponseEntity<String> peselValidator(@RequestBody Doctor doctor, Matcher matcher) {
        if (matcher.matches()) {
            try {
                doctorRepository.save(doctor);
                return new ResponseEntity<>("Zapisano", HttpStatus.CREATED);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("Nie udało się dodać lekarza", HttpStatus.NOT_MODIFIED);
            }
        } else {
            return new ResponseEntity<>("Nie zapisano, długość numeru pesel jest niepoprawna, bądź użyto niedozwolonych znaków lub liter", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(path = "/doctors")
    public @ResponseBody
    ResponseEntity<List<Doctor>> getAll() {
        return new ResponseEntity<>(doctorRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/doctors/{pesel}")
    public ResponseEntity<Doctor> getPatientByPesel(@PathVariable String pesel) {
        return new ResponseEntity<>(doctorRepository.getDoctorByPesel(pesel), HttpStatus.OK);
    }

    @DeleteMapping(path = "/doctors/{id}")
    public @ResponseBody
    ResponseEntity<String> deleteDoctorById(@PathVariable Integer id) {
        try {
            doctorRepository.deleteById(id);
            return new ResponseEntity<>("Lekarz o podanym id został usunięty.", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Nie usunięto, wpis o podanym id nie istnieje", HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<Doctor> replaceDoctor(@RequestBody Doctor newDoctor, @PathVariable Integer id) {
        Optional<Doctor> lekarzPoZmianie = doctorRepository.findById(id).map(doctor -> {
            if (newDoctor.getName() != null) {
                doctor.setName(newDoctor.getName());
            }
            if (newDoctor.getSurname() != null) {
                doctor.setSurname(newDoctor.getSurname());
            }
            if (newDoctor.getPesel() != null) {
                doctor.setPesel(newDoctor.getPesel());
            }
            return doctorRepository.save(doctor);
        });

        if (lekarzPoZmianie.isEmpty()) {
            return new ResponseEntity<Doctor>(HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<Doctor>(lekarzPoZmianie.get(), HttpStatus.OK);
        }
    }


}
