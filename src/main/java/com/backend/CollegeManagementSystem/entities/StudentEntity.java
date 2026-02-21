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
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false) // auto indexed by Hibernate
    private String registrationNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_subject_table",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"),
            indexes = {
                    @Index(name = "idx_ss_student", columnList = "student_id"),
                    @Index(name = "idx_ss_subject", columnList = "subject_id")
            }
    )
    @Setter(AccessLevel.NONE)
    private Set<SubjectEntity> subjects = new HashSet<>();




    // add and remove methods exists only in owning side.

    public void addSubject(SubjectEntity subject) {
        this.subjects.add(subject);
        subject.getStudents().add(this);
    }

    public void removeSubject(SubjectEntity subject) {
        this.subjects.remove(subject);
        subject.getStudents().remove(this);
    }




    // equals on the basis of Id and hashcode on the basis of getClass()

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