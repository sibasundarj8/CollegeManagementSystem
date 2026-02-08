package com.backend.CollegeManagementSystem.services;

import com.backend.CollegeManagementSystem.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.mapper = modelMapper;
    }

    // get students By id
}