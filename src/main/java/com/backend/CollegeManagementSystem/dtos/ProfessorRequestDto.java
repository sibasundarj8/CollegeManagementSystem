package com.backend.CollegeManagementSystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class ProfessorRequestDto {
    // use validations here not in entity...

    @NotBlank(message = "Title can't be blank")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Only characters allowed")
    @Size(min = 6, max = 30, message = "Size must be in between 6 ans 30")
    private String title;

    private final List<SubjectRequestDto> subjects = new ArrayList<>();
    private final List<StudentRequestDto> students = new ArrayList<>();
}