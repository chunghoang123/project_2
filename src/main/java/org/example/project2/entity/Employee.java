package org.example.project2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ten khong duoc de trong")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Tuoi khong duoc de trong")
    @Min(value = 18, message = "Tuoi phai tu 18 tro len")
    @Column(nullable = false)
    private Integer age;

    @Column(length = 255)
    private String avatar;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
