package org.example.project2.repository;

import org.example.project2.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE " +
            "(:name IS NULL OR :name = '' OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:departmentId IS NULL OR e.department.id = :departmentId) AND " +
            "(:minAge IS NULL OR e.age >= :minAge) AND " +
            "(:maxAge IS NULL OR e.age <= :maxAge)")
    Page<Employee> searchEmployees(
            @Param("name") String name,
            @Param("departmentId") Long departmentId,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge,
            Pageable pageable);

    List<Employee> findByDepartmentId(Long departmentId);

    @Modifying
    @Query("UPDATE Employee e SET e.department = null WHERE e.department.id = :departmentId")
    int unassignEmployeesFromDepartment(@Param("departmentId") Long departmentId);
}
