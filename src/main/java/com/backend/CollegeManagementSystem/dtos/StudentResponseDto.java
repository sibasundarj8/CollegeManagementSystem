package com.backend.CollegeManagementSystem.dtos;

import java.util.List;

public record StudentResponseDto(Long id, String name, List<String> subjects, List<String> professors) {
}