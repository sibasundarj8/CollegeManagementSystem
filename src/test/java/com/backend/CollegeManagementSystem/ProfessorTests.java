package com.backend.CollegeManagementSystem;

import com.backend.CollegeManagementSystem.dtos.request.ProfessorRequestDto;
import com.backend.CollegeManagementSystem.dtos.response.ProfessorResponseDto;
import com.backend.CollegeManagementSystem.services.ProfessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProfessorTests {

    @Autowired
    private ProfessorService service;

    @Test
    public void getProfessorByIdTest(){
        ProfessorResponseDto professor = service.getProfessorById(1L);
        System.out.println(professor);
    }

    @Test
    public void getAllProfessorsTest(){
        List<ProfessorResponseDto> professors = service.getAllProfessors();
        System.out.println(professors);
    }

    @Test
    public void getAllProfessorsByTitleTest(){
        List<ProfessorResponseDto> professors = service.getAllProfessorsByTitle("Ananya");
        System.out.println(professors);
    }

    @Test
    public void getAllSubjectOfProfessorTest(){
        List<String> subjects = service.getAllSubjectsOfProfessor(1L);
        System.out.println(subjects);
    }

    @Test
    public void createProfessorTest(){
        ProfessorRequestDto professorRequest = ProfessorRequestDto.builder().title("Ananya Das").build();
        ProfessorResponseDto professor = service.createProfessor(professorRequest);
        System.out.println(professor);
    }
}