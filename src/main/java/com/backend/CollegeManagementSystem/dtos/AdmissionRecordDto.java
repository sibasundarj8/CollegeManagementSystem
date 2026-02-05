package com.backend.CollegeManagementSystem.dtos;

import com.backend.CollegeManagementSystem.entities.StudentEntity;

public record AdmissionRecordDto(Integer fees, StudentEntity student) {
}