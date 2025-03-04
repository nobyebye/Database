package database.Hospital.controller;

import database.Hospital.model.Departments;
import database.Hospital.model.Doctors;
import database.Hospital.repository.DoctorRepository;
import database.Hospital.repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    // 获取所有医生并计算年龄
    @GetMapping
    public String listDoctors(Model model) {
        List<Doctors> doctors = doctorRepository.findAll();
        List<Departments> departments = departmentsRepository.findAll();
        model.addAttribute("doctors", doctors);
        model.addAttribute("departments", departments);
        return "doctor-list";
    }

    @GetMapping("/new")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctors());
        model.addAttribute("departments", departmentsRepository.findAll());
        return "add-doctor";
    }

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute Doctors doctor, Model model) {
        StringBuilder errors = new StringBuilder();

        if (doctorRepository.existsByDoctorNameAndEmail(doctor.getDoctorName(), doctor.getEmail())) {
            errors.append("A doctor with this name and email already exists. ");
        }

        if (doctor.getDoctorName() == null || doctor.getDoctorName().trim().isEmpty()) {
            errors.append("Doctor Name cannot be empty. ");
        }

        if (doctor.getSpecialization() == null || doctor.getSpecialization().trim().isEmpty()) {
            errors.append("Specialization cannot be empty. ");
        }

        if (doctor.getDob() == null) {
            errors.append("Birthdate cannot be null. ");
        } else if (!doctor.getDob().isBefore(LocalDate.now())) {
            errors.append("Birthdate must be in the past. ");
        }

        if (doctor.getGender() == null || doctor.getGender().isEmpty() ||
                !(doctor.getGender().equalsIgnoreCase("Male") || doctor.getGender().equalsIgnoreCase("Female"))) {
            errors.append("Gender must be 'Male' or 'Female'. ");
        }

        if (doctor.getEmail() == null || !doctor.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.append("Invalid email format. ");
        }

        if (errors.length() > 0) {
            model.addAttribute("error", errors.toString());
            model.addAttribute("departments", departmentsRepository.findAll());
            return "add-doctor";
        }

        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model) {
        Doctors doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID: " + id));
        model.addAttribute("doctor", doctor);
        model.addAttribute("departments", departmentsRepository.findAll()); // ✅ 确保前端获取科室列表
        return "edit-doctor";
    }

    @PostMapping("/update")
    public String updateDoctor(@ModelAttribute Doctors doctor, @RequestParam(name = "departmentId", required = false) Long departmentId) {
        // 1. 重新加载医生数据，确保 JOIN 关系不丢失
        Doctors existingDoctor = doctorRepository.findById(doctor.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID: " + doctor.getDoctorId()));

        // 2. 更新基本信息
        existingDoctor.setDoctorName(doctor.getDoctorName());
        existingDoctor.setDob(doctor.getDob());
        existingDoctor.setGender(doctor.getGender());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setEmail(doctor.getEmail());

        // 3. 确保 departmentId 传递正确
        if (departmentId != null) {
            Departments department = departmentsRepository.findById(departmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid department ID: " + departmentId));
            existingDoctor.setDepartment(department);
        } else {
            existingDoctor.setDepartment(null); // 避免 department 为空时报错
        }

        // 4. 保存更新后的医生信息
        doctorRepository.save(existingDoctor);
        return "redirect:/doctors";
    }




    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return "redirect:/doctors";
    }
    @GetMapping("/department")
    public String getDoctorsByDepartment(@RequestParam(required = false) String departmentName, Model model) {
        List<Doctors> doctors;

        if (departmentName == null || departmentName.isEmpty()) {
            doctors = doctorRepository.findAll();
        } else {
            doctors = doctorRepository.findDoctorsByDepartment(departmentName);
        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("departments", departmentsRepository.findAll());
        model.addAttribute("selectedDepartment", departmentName);
        return "doctor-list";
    }

    @GetMapping("/check-duplicate")
    @ResponseBody
    public Map<String, Boolean> checkDuplicateDoctor(@RequestParam String doctorName, @RequestParam String email) {
        boolean exists = doctorRepository.existsByDoctorNameAndEmail(doctorName, email);
        return Collections.singletonMap("duplicate", exists);
    }



}
