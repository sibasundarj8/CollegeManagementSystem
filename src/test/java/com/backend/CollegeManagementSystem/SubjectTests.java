package com.backend.CollegeManagementSystem;

import com.backend.CollegeManagementSystem.dtos.request.SubjectRequestDto;
import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.services.SubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubjectTests {

    @Autowired
    private SubjectService service;

    @Test
    void findSubjectByIdTest() {
        SubjectResponseDto subject = service.getSubjectById(1L);
        System.out.println(subject);
    }

    @Test
    void findSubjectByTitleTest() {
        SubjectResponseDto subject = service.getSubjectByTitle("Data Structures and Algorithms");
        System.out.println(subject);
    }

    @Test
    void findAllSubjectsTest() {
        service.getAllSubjects().forEach(System.out::println);
    }

    @Test
    void createSubjectTest() {
        SubjectRequestDto subjectRequest = SubjectRequestDto.builder()
                .title("Data Structures and algorithms")
                .build();

        SubjectResponseDto response = service.createSubject(subjectRequest);
        System.out.println(response);
    }

    @Test
    void assignSubjectToProfessorTest() {
        SubjectResponseDto response = service.assignProfessor(1L, 1L);
        System.out.println(response);
    }

    @Test
    void unassignSubjectFromProfessorTest() {
        SubjectResponseDto response = service.unassignProfessor(1L, 1L);
        System.out.println(response);
    }

    @Test
    void deleteSubjectTest() {
        service.deleteSubjectById(1L);
    }
}