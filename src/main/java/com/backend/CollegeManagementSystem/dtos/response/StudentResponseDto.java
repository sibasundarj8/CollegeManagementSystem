package com.backend.CollegeManagementSystem.dtos.response;

import java.util.Set;

public record StudentResponseDto(Long id, String name, String registrationNumber, Set<SubjectResponseDto> subjects) {
}