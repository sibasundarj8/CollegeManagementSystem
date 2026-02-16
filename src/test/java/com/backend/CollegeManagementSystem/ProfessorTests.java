package com.backend.CollegeManagementSystem;

import com.backend.CollegeManagementSystem.services.ProfessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProfessorTests {

    @Autowired
    private ProfessorService service;

    @Test
    public void getStudentByIdTest(){
    }
}