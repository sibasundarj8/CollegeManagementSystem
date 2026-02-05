package com.backend.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @OneToMany(mappedBy = "professor")
    private List<SubjectEntity> subjects;

    @ManyToMany(mappedBy = "professors")
    private List<StudentEntity> students;
}