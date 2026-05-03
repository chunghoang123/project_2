package org.example.project2.controller;

import org.example.project2.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "departments/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            int affectedEmployees = departmentService.deleteDepartmentAndUnassignEmployees(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Da xoa phong ban va cap nhat trang thai cho " + affectedEmployees + " nhan vien.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Khong the xoa phong ban: " + ex.getMessage());
        }
        return "redirect:/departments";
    }
}
