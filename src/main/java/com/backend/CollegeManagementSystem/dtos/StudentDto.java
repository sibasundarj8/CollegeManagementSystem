package com.backend.CollegeManagementSystem.dtos;

import com.backend.CollegeManagementSystem.entities.AdmissionRecordEntity;
import com.backend.CollegeManagementSystem.entities.ProfessorEntity;
import com.backend.CollegeManagementSystem.entities.SubjectEntity;

import java.util.List;

public record StudentDto(String name, AdmissionRecordEntity admissionRecord, List<SubjectEntity> subjects, List<ProfessorEntity> professors) {
}