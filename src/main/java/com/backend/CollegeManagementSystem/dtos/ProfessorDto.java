package com.backend.CollegeManagementSystem.dtos;

import java.util.List;

public record ProfessorDto(String title, List<SubjectDto> subjects) {
}