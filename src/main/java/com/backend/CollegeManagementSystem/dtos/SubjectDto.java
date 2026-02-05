package com.backend.CollegeManagementSystem.dtos;

import com.backend.CollegeManagementSystem.entities.ProfessorEntity;
import com.backend.CollegeManagementSystem.entities.StudentEntity;

import java.util.List;

public record SubjectDto(String title, ProfessorEntity professor, List<StudentEntity> students) {
}