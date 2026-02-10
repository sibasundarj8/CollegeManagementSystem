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
@Table(indexes = {
        @Index(name = "idx_subject_professor", columnList = "professor_id"),
        @Index(name = "idx_subject_code", columnList = "subject_code", unique = true)
})
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    @JsonIgnore
    private ProfessorEntity professor;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subjects")
    @JsonIgnore
    private Set<StudentEntity> students = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectEntity)) return false;
        return id != null && id.equals(((SubjectEntity) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}