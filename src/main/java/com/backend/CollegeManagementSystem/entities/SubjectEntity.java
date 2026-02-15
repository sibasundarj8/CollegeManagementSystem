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
@Table(indexes = @Index(name = "idx_subject_professor", columnList = "professor_id"))
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    @JsonIgnore
    private ProfessorEntity professor;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subjects")
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private Set<StudentEntity> students = new HashSet<>();




    // equals on the basis of Id and hashcode on the basis of getClass()

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