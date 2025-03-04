package database.Hospital.controller;

import database.Hospital.model.Appointments;
import database.Hospital.model.AppointmentStatus;
import database.Hospital.model.Doctors;
import database.Hospital.model.Patients;
import database.Hospital.repository.AppointmentsRepository;
import database.Hospital.repository.DoctorRepository;
import database.Hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/appointments")
public class AppointmentsController {

    private final AppointmentsRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentsController(AppointmentsRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping("/doctor/{doctorId}")
    public String listAppointmentsByDoctor(@PathVariable Long doctorId, Model model) {
        List<Appointments> appointments = appointmentRepository.findByDoctorDoctorId(doctorId);
        model.addAttribute("appointments", appointments);
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("selectedDoctorId", doctorId);
        return "appointment-list";
    }
    @GetMapping
    public String listAppointments(@RequestParam(value = "doctorId", required = false) Long doctorId, Model model) {
        List<Appointments> appointments;

        if (doctorId != null) {
            appointments = appointmentRepository.findByDoctorDoctorId(doctorId);
            model.addAttribute("selectedDoctorId", doctorId);
        } else {
            appointments = appointmentRepository.findAll();
        }

        model.addAttribute("appointments", appointments);
        model.addAttribute("doctors", doctorRepository.findAll());
        return "appointment-list";
    }

    @GetMapping("/new")
    public String showAddAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointments());
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        return "add-appointment";
    }

    @PostMapping("/save")
    public String saveAppointment(@RequestParam("doctorId") Long doctorId,
                                  @RequestParam("patientId") Long patientId,
                                  @RequestParam("status") String status,
                                  @ModelAttribute Appointments appointment,
                                  Model model) {
        try {
            Optional<Doctors> doctorOpt = doctorRepository.findById(doctorId);
            Optional<Patients> patientOpt = patientRepository.findById(patientId);

            if (doctorOpt.isPresent() && patientOpt.isPresent()) {
                appointment.setDoctor(doctorOpt.get());
                appointment.setPatient(patientOpt.get());

                try {
                    AppointmentStatus appointmentStatus = AppointmentStatus.fromString(status);
                    appointment.setStatus(appointmentStatus);
                } catch (IllegalArgumentException e) {
                    model.addAttribute("error", "Invalid reservation status: " + status);
                    model.addAttribute("doctors", doctorRepository.findAll());
                    model.addAttribute("patients", patientRepository.findAll());
                    return "add-appointment";
                }

                appointmentRepository.save(appointment);
                return "redirect:/appointments";
            } else {
                model.addAttribute("error", "Doctor or patient does not exist, please select again!");
                model.addAttribute("doctors", doctorRepository.findAll());
                model.addAttribute("patients", patientRepository.findAll());
                return "add-appointment";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred, please try again!");
            model.addAttribute("doctors", doctorRepository.findAll());
            model.addAttribute("patients", patientRepository.findAll());
            return "add-appointment";
        }
    }

    @GetMapping("/edit/{id}")
    public String editAppointment(@PathVariable Long id, Model model) {
        Optional<Appointments> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            model.addAttribute("appointment", appointmentOpt.get());
            model.addAttribute("doctors", doctorRepository.findAll());
            model.addAttribute("patients", patientRepository.findAll());
            return "edit-appointment";
        } else {
            return "redirect:/appointments";
        }
    }

    @PostMapping("/update")
    public String updateAppointment(@RequestParam Long id,
                                    @RequestParam Long doctorId,
                                    @RequestParam Long patientId,
                                    @RequestParam String status,
                                    Model model) {
        Optional<Appointments> appointmentOpt = appointmentRepository.findById(id);
        Optional<Doctors> doctorOpt = doctorRepository.findById(doctorId);
        Optional<Patients> patientOpt = patientRepository.findById(patientId);

        if (appointmentOpt.isPresent() && doctorOpt.isPresent() && patientOpt.isPresent()) {
            Appointments appointment = appointmentOpt.get();
            appointment.setDoctor(doctorOpt.get());
            appointment.setPatient(patientOpt.get());

            try {
                AppointmentStatus appointmentStatus = AppointmentStatus.fromString(status);
                appointment.setStatus(appointmentStatus);
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", "Invalid reservation status: " + status);
                model.addAttribute("doctors", doctorRepository.findAll());
                model.addAttribute("patients", patientRepository.findAll());
                return "edit-appointment";
            }

            appointmentRepository.save(appointment);
            return "redirect:/appointments";
        } else {
            model.addAttribute("error", "The appointment information is invalid, please check if the doctor and patient exist");
            return "edit-appointment";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
        return "redirect:/appointments";
    }


}
