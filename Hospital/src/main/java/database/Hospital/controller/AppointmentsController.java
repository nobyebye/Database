package database.Hospital.controller;

import database.Hospital.model.Appointments;
import database.Hospital.model.Doctors;
import database.Hospital.model.Patients;
import database.Hospital.repository.AppointmentsRepository;
import database.Hospital.repository.DoctorRepository;
import database.Hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentsController {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // 显示所有预约
    @GetMapping
    public String listAppointments(Model model) {
        List<Appointments> appointments = appointmentsRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "appointment-list"; // Thymeleaf 模板
    }

    // 显示添加预约页面
    @GetMapping("/new")
    public String showAddAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointments());
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        return "add-appointment";
    }

    // 处理添加预约请求
    @PostMapping("/save")
    public String saveAppointment(
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("patientId") Long patientId,
            @RequestParam("bookingDate") String bookingDate,
            @RequestParam("time") String time,
            @RequestParam(value = "status", defaultValue = "Scheduled") String status,
            @RequestParam(value = "paymentStatus", defaultValue = "Pending") String paymentStatus
    ) {
        // 查找医生和病人
        Doctors doctor = doctorRepository.findById(doctorId).orElse(null);
        Patients patient = patientRepository.findById(patientId).orElse(null);

        if (doctor == null || patient == null) {
            return "redirect:/appointments?error=InvalidDoctorOrPatient";
        }

        // 解析日期和时间
        LocalDate parsedBookingDate = LocalDate.parse(bookingDate);
        LocalTime parsedTime = LocalTime.parse(time);

        // 创建新的预约对象
        Appointments appointment = new Appointments(doctor, patient, parsedBookingDate, parsedTime, status, paymentStatus);

        // 保存到数据库
        appointmentsRepository.save(appointment);
        return "redirect:/appointments";
    }

    // 删除预约
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentsRepository.deleteById(id);
        return "redirect:/appointments";
    }
}
