package com.backend.CollegeManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(indexes = @Index(name = "idx_student_name", columnList = "name"))
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "student_professor_table",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id"),
            indexes = {
                    @Index(name = "idx_sp_student", columnList = "student_id"),
                    @Index(name = "idx_sp_professor", columnList = "professor_id")
            }
    )
    private List<ProfessorEntity> professors = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "student_subject_table",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"),
            indexes = {
                    @Index(name = "idx_ss_student", columnList = "student_id"),
                    @Index(name = "idx_ss_subject", columnList = "subject_id")
            }
    )
    private List<SubjectEntity> subjects = new ArrayList<>();

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private AdmissionRecordEntity admissionRecord;
}