package org.example.project2.service;

import org.example.project2.entity.Department;
import org.example.project2.repository.DepartmentRepository;
import org.example.project2.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository,
                             EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteDepartmentAndUnassignEmployees(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay phong ban voi ID: " + departmentId));

        int affectedEmployees = employeeRepository.unassignEmployeesFromDepartment(departmentId);

        departmentRepository.delete(department);

        return affectedEmployees;
    }
}
