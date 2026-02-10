package com.backend.CollegeManagementSystem.dtos.request;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionRecordRequestDto {
    // use validations here not in entity...

    @Positive
    private Integer fees;

    private StudentRequestDto student;
}