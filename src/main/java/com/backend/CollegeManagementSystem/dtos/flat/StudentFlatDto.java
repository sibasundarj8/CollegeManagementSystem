package com.backend.CollegeManagementSystem.dtos.flat;

public record StudentFlatDto(Long id, String name, String registrationNumber, Long subjectId, String subjectTitle, String professorName) {
}