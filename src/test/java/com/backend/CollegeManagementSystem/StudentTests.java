package com.backend.CollegeManagementSystem;

import com.backend.CollegeManagementSystem.dtos.response.StudentResponseDto;
import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StudentTests {

    @Autowired
    private StudentService studentService;

    @Test
    void getAllStudentsTest() {
        List<StudentResponseDto> students = studentService.getAllStudents();
        System.out.println(students);
    }

    @Test
    void getStudentByIdTest() {
        StudentResponseDto student = studentService.getStudentById(1L);
        System.out.println(student);
    }

    @Test
    void getStudentsByNameTest() {
        List<StudentResponseDto> students = studentService.getStudentsByName("Sibasundar");
        System.out.println(students);
    }

    @Test
    void getStudentsByRegistrationNumberTest() {
        StudentResponseDto student = studentService.getStudentsByRegistrationNumber("2301326236");
        System.out.println(student);
    }

    @Test
    void getSubjectsOfStudentByIdTest() {
        List<SubjectResponseDto> subjects = studentService.getSubjectsOfStudentById(1L);
        System.out.println(subjects);
    }
}