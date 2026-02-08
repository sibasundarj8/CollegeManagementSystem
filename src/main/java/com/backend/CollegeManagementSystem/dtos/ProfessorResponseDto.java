package com.backend.CollegeManagementSystem.dtos;

import java.util.List;

public record ProfessorResponseDto(Long id, String title, List<String> subjects) {
}