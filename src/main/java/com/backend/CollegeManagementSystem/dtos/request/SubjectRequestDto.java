package com.backend.CollegeManagementSystem.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRequestDto {
    // use validations here not in entity...

    @NotBlank(message = "Subject name can't be blank")
    @Pattern(regexp = "^[A-Za-z]+( [A-Za-z]+)*( [0-9]+)?$",
            message = "Subject name must contain only letters, spaces, and optional number at end")
    @Size(min = 4, max = 40, message = "Size must be in between 4 ans 40")
    private String title;

    private Long professorId;
}