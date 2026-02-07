package com.backend.CollegeManagementSystem.dtos;

import java.util.List;

public record StudentDto(String name, List<SubjectDto> subjects, List<ProfessorDto> professors) {
}