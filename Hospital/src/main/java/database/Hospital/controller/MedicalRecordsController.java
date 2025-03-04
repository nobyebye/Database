package database.Hospital.controller;

import database.Hospital.model.*;
import database.Hospital.repository.MedicalRecordsRepository;
import database.Hospital.repository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/medical-records")
public class MedicalRecordsController {

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    // 显示所有医疗记录
    @GetMapping
    public String listMedicalRecords(Model model) {
        List<MedicalRecords> medicalRecords = medicalRecordsRepository.findAll();
        model.addAttribute("medicalRecords", medicalRecords);
        return "medical-record-list";
    }

    // 显示添加医疗记录页面
    @GetMapping("/new")
    public String showAddMedicalRecordForm(Model model) {
        model.addAttribute("medicalRecord", new MedicalRecords());

        List<Appointments> completedAppointments = appointmentsRepository.findByStatus(AppointmentStatus.COMPLETED);
        model.addAttribute("appointments", completedAppointments);

        return "add-medical-record";
    }

    @PostMapping("/save")
    public String saveMedicalRecord(
            @RequestParam("appointmentId") Long appointmentId,
            @RequestParam("diagnosis") String diagnosis,
            @RequestParam("treatmentPlan") String treatmentPlan,
            @RequestParam(value = "prescription", required = false) String prescription,
            @RequestParam(value = "testResults", required = false) String testResults,
            Model model
    ) {

        Optional<Appointments> appointmentOpt = appointmentsRepository.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            model.addAttribute("error", "预约 ID 无效！");
            model.addAttribute("appointments", appointmentsRepository.findAll());
            return "add-medical-record";
        }

        Appointments appointment = appointmentOpt.get();


        if (appointment.getStatus() == AppointmentStatus.INCOMPLETE) {
            model.addAttribute("error", "该预约尚未完成，无法添加医疗记录！");
            model.addAttribute("appointments", appointmentsRepository.findAll());
            return "add-medical-record";
        }


        Patients patient = appointment.getPatient();
        Doctors doctor = appointment.getDoctor();
        LocalDate visitDate = appointment.getBookingDate();

        if (patient == null || doctor == null) {
            model.addAttribute("error", "该预约的医生或患者信息不完整！");
            return "add-medical-record";
        }

        MedicalRecords medicalRecord = new MedicalRecords();
        medicalRecord.setAppointment(appointment);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setPatient(patient);
        medicalRecord.setDiagnosis(diagnosis);
        medicalRecord.setTreatmentPlan(treatmentPlan);
        medicalRecord.setPrescription(prescription);
        medicalRecord.setTestResults(testResults);
        medicalRecord.setVisitDate(visitDate);

        medicalRecordsRepository.save(medicalRecord);
        model.addAttribute("success", "医疗记录添加成功！");
        return "redirect:/medical-records";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicalRecord(@PathVariable Long id, Model model) {
        if (!medicalRecordsRepository.existsById(id)) {
            model.addAttribute("error", "医疗记录不存在！");
            return "medical-record-list";
        }
        medicalRecordsRepository.deleteById(id);
        model.addAttribute("success", "医疗记录删除成功！");
        return "redirect:/medical-records";
    }
}
