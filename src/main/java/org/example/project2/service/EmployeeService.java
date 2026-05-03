package org.example.project2.service;

import org.example.project2.entity.Employee;
import org.example.project2.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Page<Employee> searchEmployees(String name, Long departmentId,
                                          Integer minAge, Integer maxAge,
                                          int page, int size,
                                          String sortField, String sortDir) {
        if (page < 0) {
            page = 0;
        }

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        String searchName = (name != null && !name.trim().isEmpty()) ? name.trim() : null;

        return employeeRepository.searchEmployees(searchName, departmentId, minAge, maxAge, pageable);
    }
}
