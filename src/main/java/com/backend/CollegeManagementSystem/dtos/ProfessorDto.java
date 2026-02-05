package com.backend.CollegeManagementSystem.dtos;

import com.backend.CollegeManagementSystem.entities.StudentEntity;
import com.backend.CollegeManagementSystem.entities.SubjectEntity;

import java.util.List;

public record ProfessorDto(String title, List<SubjectEntity> subjects, List<StudentEntity> students) {
}