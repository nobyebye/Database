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

    @GetMapping
    public String listDepartments(Model model) {
        List<Departments> departments = departmentsRepository.findAll();
        model.addAttribute("departments", departments);
        return "department-list";
    }

    @GetMapping("/new")
    public String showAddDepartmentForm(Model model) {
        model.addAttribute("department", new Departments());
        return "add-department";
    }

    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute Departments department, Model model) {
        Optional<Departments> existingDepartment = departmentsRepository.findByDepartmentName(department.getDepartmentName());

        if (existingDepartment.isPresent()) {
            model.addAttribute("error", "This department already exists！");
            model.addAttribute("department", department);
            return "add-department";
        }

        departmentsRepository.save(department);
        return "redirect:/departments";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id, Model model) {
        if (!departmentsRepository.existsById(id)) {
            model.addAttribute("error", "The department does not exist!");
            return "department-list";
        }
        departmentsRepository.deleteById(id);
        model.addAttribute("success", "The department was deleted successfully!");
        return "redirect:/departments";
    }
}
