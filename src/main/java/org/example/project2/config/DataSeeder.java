package org.example.project2.config;

import org.example.project2.entity.Department;
import org.example.project2.entity.Employee;
import org.example.project2.repository.DepartmentRepository;
import org.example.project2.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DataSeeder(DepartmentRepository departmentRepository,
                      EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Kiem tra du lieu da ton tai chua
        if (departmentRepository.count() == 0 && employeeRepository.count() == 0) {

            // Tao Department truoc
            Department itDept = Department.builder()
                    .name("Information Technology")
                    .location("Hanoi - Floor 5")
                    .build();

            Department hrDept = Department.builder()
                    .name("Human Resources")
                    .location("Hanoi - Floor 3")
                    .build();

            Department salesDept = Department.builder()
                    .name("Sales & Marketing")
                    .location("Ho Chi Minh - Floor 7")
                    .build();

            departmentRepository.save(itDept);
            departmentRepository.save(hrDept);
            departmentRepository.save(salesDept);

            // Tao Employee va gan Department
            Employee emp1 = Employee.builder()
                    .name("Nguyen Van A")
                    .age(28)
                    .avatar("https://i.pravatar.cc/150?img=1")
                    .status(true)
                    .department(itDept)
                    .build();

            Employee emp2 = Employee.builder()
                    .name("Tran Thi B")
                    .age(32)
                    .avatar("https://i.pravatar.cc/150?img=2")
                    .status(true)
                    .department(itDept)
                    .build();

            Employee emp3 = Employee.builder()
                    .name("Le Van C")
                    .age(25)
                    .avatar("https://i.pravatar.cc/150?img=3")
                    .status(false)
                    .department(hrDept)
                    .build();

            Employee emp4 = Employee.builder()
                    .name("Pham Thi D")
                    .age(30)
                    .avatar("https://i.pravatar.cc/150?img=4")
                    .status(true)
                    .department(salesDept)
                    .build();

            Employee emp5 = Employee.builder()
                    .name("Hoang Van E")
                    .age(45)
                    .avatar("https://i.pravatar.cc/150?img=5")
                    .status(true)
                    .department(salesDept)
                    .build();

            employeeRepository.save(emp1);
            employeeRepository.save(emp2);
            employeeRepository.save(emp3);
            employeeRepository.save(emp4);
            employeeRepository.save(emp5);

            System.out.println("Seeding hoan tat: 3 phong ban va 5 nhan vien da duoc them!");
        } else {
            System.out.println("Du lieu da ton tai, bo qua seeding.");
        }
    }
}
