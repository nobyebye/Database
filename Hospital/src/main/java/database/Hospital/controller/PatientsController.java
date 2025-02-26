package database.Hospital.controller;

import database.Hospital.model.Patients;
import database.Hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientsController {

    @Autowired
    private PatientRepository patientsRepository;

    // 显示所有病人
    @GetMapping
    public String listPatients(Model model) {
        List<Patients> patients = patientsRepository.findAll();
        model.addAttribute("patients", patients);
        return "patient-list"; // Thymeleaf 模板
    }

    // 显示添加病人页面
    @GetMapping("/new")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patients());
        return "add-patient";
    }

    // 处理添加病人请求
    @PostMapping("/save")
    public String savePatient(
            @RequestParam("name") String name,
            @RequestParam("gender") String gender,
            @RequestParam("dob") String dob,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "address", required = false) String address,
            Model model
    ) {
        // 解析日期
        LocalDate dateOfBirth;
        try {
            dateOfBirth = LocalDate.parse(dob);
        } catch (Exception e) {
            model.addAttribute("error", "出生日期格式错误！");
            return "add-patient";
        }

        // 检查邮箱是否已注册
        if (email != null && patientsRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "该邮箱已被注册！");
            return "add-patient";
        }

        // 创建 Patient 对象
        Patients patient = new Patients();
        patient.setPatientname(name);  // **修改为 `setPatientname()`**
        patient.setGender(gender);
        patient.setDob(dateOfBirth);
        patient.setEmail(email);
        patient.setAddress(address);

        // 保存到数据库
        patientsRepository.save(patient);
        model.addAttribute("success", "病人添加成功！");
        return "redirect:/patients";
    }


    // 删除病人
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id, Model model) {
        if (!patientsRepository.existsById(id)) {
            model.addAttribute("error", "病人不存在！");
            return "patient-list";
        }
        patientsRepository.deleteById(id);
        model.addAttribute("success", "病人删除成功！");
        return "redirect:/patients";
    }
}
