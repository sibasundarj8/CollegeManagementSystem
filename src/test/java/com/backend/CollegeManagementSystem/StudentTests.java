package com.backend.CollegeManagementSystem;

import com.backend.CollegeManagementSystem.dtos.request.StudentRequestDto;
import com.backend.CollegeManagementSystem.dtos.response.StudentResponseDto;
import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
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
        StudentResponseDto student = studentService.getStudentByRegistrationNumber("2301326236");
        System.out.println(student);
    }

    @Test
    void getSubjectsOfStudentByIdTest() {
        List<SubjectResponseDto> subjects = studentService.getSubjectsOfStudentById(1L);
        System.out.println(subjects);
    }

    @Test
    void createStudentTest() {
        StudentRequestDto student = new StudentRequestDto();
        student.setName("Sidharth Sourabh Mishra");
        student.setRegistrationNumber("2301326238");

        List<Long> subjectIds = new ArrayList<>();
        subjectIds.add(1L);
        subjectIds.add(2L);

        student.setSubjectIds(subjectIds);
        student.setFees(45000);

        StudentResponseDto response = studentService.createStudent(student);
        System.out.println(response);
    }

    @Test
    void assignSubjectToStudentTest() {
        StudentResponseDto student = studentService.assignSubject(2L, 9L);
        System.out.println(student);
    }

    @Test
    void unassignSubjectFromStudentTest() {
        StudentResponseDto student = studentService.unassignSubject(1L, 9L);
        System.out.println(student);
    }
}