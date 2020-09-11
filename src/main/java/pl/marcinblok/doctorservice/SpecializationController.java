package pl.marcinblok.doctorservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpecializationController {

    @Autowired
    private SpecializationRepository specializationRepository;



    @PostMapping(path = "/specializations")
    public @ResponseBody
    ResponseEntity<String> add(@RequestBody List<Specialization> specializations) {
        if(isSpecializationEmpty(specializations)){
            for (Specialization specialization: specializations) {
                specializationRepository.save(specialization);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public boolean isSpecializationEmpty(List<Specialization> specializations) {
        for (Specialization specialization : specializations) {
            if (specialization.getName() == null) {
                return false;
            }
        }
        return true;
    }


    @GetMapping(path = "/specializations")
    public @ResponseBody
    ResponseEntity<List<Specialization>> getAll() {
        return new ResponseEntity<>(specializationRepository.findAll(), HttpStatus.OK);
    }


}
