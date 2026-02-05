package com.backend.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
    private List<SubjectEntity> subjects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "professors")
    private List<StudentEntity> students = new ArrayList<>();
}