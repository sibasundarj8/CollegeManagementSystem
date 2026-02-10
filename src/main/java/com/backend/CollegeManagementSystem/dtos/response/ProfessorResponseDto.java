package com.backend.CollegeManagementSystem.dtos.response;

import java.util.List;

public record ProfessorResponseDto(Long id, String title, List<String> subjects) {
}