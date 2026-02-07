package com.backend.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionRecordEntity {

    @Id  // @GeneratedValue not required here because we add @MapsId here, means it adds student id as its primary key.
    private Long id;

    private Integer fees;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // save student automatically
    @MapsId
    @JoinColumn(name = "student_id", unique = true)
    private StudentEntity student;
}