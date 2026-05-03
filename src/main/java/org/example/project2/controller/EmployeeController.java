package org.example.project2.controller;

import jakarta.validation.Valid;
import org.example.project2.entity.Employee;
import org.example.project2.repository.DepartmentRepository;
import org.example.project2.repository.EmployeeRepository;
import org.example.project2.service.EmployeeService;
import org.example.project2.service.FileStorageService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final FileStorageService fileStorageService;
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeRepository employeeRepository,
                              DepartmentRepository departmentRepository,
                              FileStorageService fileStorageService,
                              EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.fileStorageService = fileStorageService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listEmployees(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Page<Employee> employeePage = employeeService.searchEmployees(
                name, departmentId, minAge, maxAge, page, size, sortField, sortDir);

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("employees", employeePage.getContent());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("totalItems", employeePage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("name", name);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("minAge", minAge);
        model.addAttribute("maxAge", maxAge);

        return "employees/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employees/form";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult bindingResult,
                               @RequestParam("file") MultipartFile file,
                               Model model) throws Exception {

        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            return "employees/form";
        }

        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            employee.setAvatar(fileName);
        }

        employeeRepository.save(employee);
        return "redirect:/employees";
    }
}
