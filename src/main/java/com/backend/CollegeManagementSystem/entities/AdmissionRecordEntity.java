package com.backend.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AdmissionRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Integer fees;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "student_id")
    private StudentEntity student;
}