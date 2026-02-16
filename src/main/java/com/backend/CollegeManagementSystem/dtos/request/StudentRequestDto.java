package com.backend.CollegeManagementSystem.dtos.request;

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
@Builder
public class StudentRequestDto {
    // use validations here not in entity...

    @NotBlank(message = "Title can't be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Only characters allowed")
    @Size(min = 6, max = 30, message = "Size must be in between 6 ans 30")
    private String name;

    @NotBlank(message = "Registration number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Registration number must be exactly 10 digits")
    private String registrationNumber;

    private List<Long> subjectIds = new ArrayList<>();
    private Integer fees;
}