package database.Hospital.controller;

import database.Hospital.model.Patients;
import database.Hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/patients")
public class PatientsController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public String listPatients(Model model) {
        List<Patients> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "patient-list";
    }

    @GetMapping("/new")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patients());
        return "add-patient";
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patients patient, Model model) {
        Patients existingPatient = patientRepository.findByEmail(patient.getEmail());
        if (existingPatient != null) {
            model.addAttribute("error", "该邮箱已被使用！");
            model.addAttribute("patient", patient);
            return "add-patient";
        }
        patientRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String editPatient(@PathVariable Long id, Model model) {
        Patients patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient ID: " + id));
        model.addAttribute("patient", patient);
        return "edit-patient";
    }

    @PostMapping("/update")
    public String updatePatient(@ModelAttribute Patients patient) {
        patientRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
        return "redirect:/patients";
    }

    @ResponseBody
    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam String email) {
        boolean exists = patientRepository.findByEmail(email) != null;
        return Collections.singletonMap("exists", exists);
    }

}
