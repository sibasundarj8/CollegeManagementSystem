package com.backend.CollegeManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = @Index(name = "idx_student_name", columnList = "name"))
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false) // auto indexed by Hibernate
    private String registrationNumber;

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
    private Set<SubjectEntity> subjects = new HashSet<>();

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private AdmissionRecordEntity admissionRecord;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentEntity)) return false;
        return id != null && id.equals(((StudentEntity) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}