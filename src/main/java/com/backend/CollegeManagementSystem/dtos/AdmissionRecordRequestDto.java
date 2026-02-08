package com.backend.CollegeManagementSystem.dtos;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class AdmissionRecordRequestDto {
    // use validations here not in entity...

    @Positive
    private Integer fees;

    private StudentRequestDto student;
}