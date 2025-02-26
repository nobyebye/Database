package database.Hospital.controller;

import database.Hospital.model.Doctors;
import database.Hospital.repository.DoctorRepository;
import database.Hospital.repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    // 获取所有医生
    @GetMapping
    public String listDoctors(Model model) {
        List<Doctors> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "doctor-list.html"; // Thymeleaf 模板
    }

    // 显示添加医生页面
    @GetMapping("/new")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctors());
        model.addAttribute("departments", departmentsRepository.findAll()); // 获取所有科室
        return "add-doctor";
    }

    // 处理添加医生请求
    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute Doctors doctor) {
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }

    // 删除医生
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return "redirect:/doctors";
    }

    // 显示编辑医生页面
    @GetMapping("/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model) {
        Doctors doctor = doctorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID: " + id));
        model.addAttribute("doctor", doctor);
        model.addAttribute("departments", departmentsRepository.findAll()); // 提供科室列表
        return "edit-doctor";
    }

    // 处理更新医生请求
    @PostMapping("/update")
    public String updateDoctor(@ModelAttribute Doctors doctor) {
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }
}
