package database.Hospital.controller;

import database.Hospital.model.MedicalRecords;
import database.Hospital.model.Appointments;
import database.Hospital.repository.MedicalRecordsRepository;
import database.Hospital.repository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        return "medical-record-list"; // Thymeleaf 模板
    }

    // 显示添加医疗记录页面
    @GetMapping("/new")
    public String showAddMedicalRecordForm(Model model) {
        model.addAttribute("medicalRecord", new MedicalRecords());
        model.addAttribute("appointments", appointmentsRepository.findAll());
        return "add-medical-record";
    }

    // 处理添加医疗记录请求
    @PostMapping("/save")
    public String saveMedicalRecord(
            @RequestParam("appointmentId") Long appointmentId,
            @RequestParam("diagnosis") String diagnosis,
            @RequestParam("treatmentPlan") String treatmentPlan,
            @RequestParam(value = "prescription", required = false) String prescription,
            @RequestParam(value = "testResults", required = false) String testResults,
            @RequestParam("visitDate") String visitDate,
            Model model
    ) {
        Optional<Appointments> appointment = appointmentsRepository.findById(appointmentId);
        if (appointment.isEmpty()) {
            model.addAttribute("error", "预约 ID 无效！");
            return "add-medical-record";
        }

        Optional<MedicalRecords> existingRecord = medicalRecordsRepository.findByAppointmentId(appointmentId);
        if (existingRecord.isPresent()) {
            model.addAttribute("error", "该预约已经有医疗记录！");
            return "add-medical-record";
        }

        MedicalRecords medicalRecord = new MedicalRecords();
        medicalRecord.setAppointment(appointment.get());
        medicalRecord.setDiagnosis(diagnosis);
        medicalRecord.setTreatmentPlan(treatmentPlan);
        medicalRecord.setPrescription(prescription);
        medicalRecord.setTestResults(testResults);
        medicalRecord.setVisitDate(LocalDate.parse(visitDate)); // **手动转换 visitDate**

        medicalRecordsRepository.save(medicalRecord);
        model.addAttribute("success", "医疗记录添加成功！");
        return "redirect:/medical-records";
    }

    // 删除医疗记录
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
