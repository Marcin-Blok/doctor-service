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


    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DayRepository dayRepository;

    @PostMapping(path = "/doctors")
    public @ResponseBody
    ResponseEntity<String> add(@RequestBody List<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            if (doctorsListValidator(doctors)) {
                saveDoctor(doctor);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }
        return new ResponseEntity<>("Zapisano", HttpStatus.CREATED);
    }

    //  Jeżeli pola wymagane nie został podane lub pesel niepoprawny to false
    public boolean doctorsListValidator(List<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            String pesel = doctor.getPesel();
            if (!isFilledRequiredFields(doctor) || !peselIsValid(pesel)) {
                return false;
            }
        }
        return true;
    }


    private boolean isFilledRequiredFields(Doctor doctor) {
        return doctor.getName() != null && doctor.getSurname() != null && doctor.getSpecialization() != null;
    }

    private void saveDoctor(Doctor doctor) {
        try {
            doctorRepository.save(doctor);
            for (Day day : doctor.getDays()) {
                day.setDoctor(doctor);
                dayRepository.save(day);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Nie udało się dodać lekarza");
        }
    }

    private boolean peselIsValid(String pesel) {
        if (pesel == null) {
            return true;
        }
        Pattern pattern = Pattern.compile("[0-9]{11}");
        Matcher matcher = pattern.matcher(pesel);
        return matcher.matches();
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
