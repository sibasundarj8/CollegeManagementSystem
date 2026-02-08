package com.backend.CollegeManagementSystem.configs;

import com.backend.CollegeManagementSystem.dtos.*;
import com.backend.CollegeManagementSystem.entities.AdmissionRecordEntity;
import com.backend.CollegeManagementSystem.entities.ProfessorEntity;
import com.backend.CollegeManagementSystem.entities.StudentEntity;
import com.backend.CollegeManagementSystem.entities.SubjectEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        /*
         * conversion path:   RequestDto --> Entity --> ResponseDto
         */

        // AdmissionRecords
        mapper.typeMap(AdmissionRecordRequestDto.class, AdmissionRecordEntity.class);
        mapper.typeMap(AdmissionRecordEntity.class, AdmissionRecordResponseDto.class);

        // Professors
        mapper.typeMap(ProfessorRequestDto.class, ProfessorEntity.class);
        mapper.typeMap(ProfessorEntity.class, ProfessorResponseDto.class);

        // Students
        mapper.typeMap(StudentRequestDto.class, StudentEntity.class);
        mapper.typeMap(StudentEntity.class, StudentResponseDto.class);

        // Subjects
        mapper.typeMap(SubjectRequestDto.class, SubjectEntity.class);
        mapper.typeMap(SubjectEntity.class, SubjectResponseDto.class);

        return mapper;
    }
}