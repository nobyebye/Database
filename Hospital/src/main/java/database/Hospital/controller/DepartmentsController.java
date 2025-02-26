package database.Hospital.controller;

import database.Hospital.model.Departments;
import database.Hospital.repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/departments")
public class DepartmentsController {

    @Autowired
    private DepartmentsRepository departmentsRepository;

    // 显示所有科室
    @GetMapping
    public String listDepartments(Model model) {
        List<Departments> departments = departmentsRepository.findAll();
        model.addAttribute("departments", departments);
        return "department-list"; // Thymeleaf 模板
    }

    // 显示添加科室页面
    @GetMapping("/new")
    public String showAddDepartmentForm(Model model) {
        model.addAttribute("department", new Departments());
        return "add-department";
    }

    // 处理添加科室请求
    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute Departments department, Model model) {
        Optional<Departments> existingDepartment = departmentsRepository.findByDepartmentName(department.getDepartmentName());
        if (existingDepartment.isPresent()) {
            model.addAttribute("error", "该科室已存在！");
            return "add-department";
        }
        departmentsRepository.save(department);
        model.addAttribute("success", "科室添加成功！");
        return "redirect:/departments";
    }

    // 删除科室
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id, Model model) {
        if (!departmentsRepository.existsById(id)) {
            model.addAttribute("error", "科室不存在！");
            return "department-list";
        }
        departmentsRepository.deleteById(id);
        model.addAttribute("success", "科室删除成功！");
        return "redirect:/departments";
    }
}
