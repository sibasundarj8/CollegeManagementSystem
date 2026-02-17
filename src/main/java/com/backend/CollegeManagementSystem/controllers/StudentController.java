package com.backend.CollegeManagementSystem.controllers;

import com.backend.CollegeManagementSystem.dtos.request.StudentRequestDto;
import com.backend.CollegeManagementSystem.dtos.response.StudentResponseDto;
import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // get all students with name : response should be 200 ok
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getStudentsByName(
            @RequestParam(value = "name", required = false) String name) {

        return ResponseEntity.ok(service.getStudentsByName(name));
    }

    // get Student by Id : response should be 200 ok
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getStudentById(id));
    }

    // get student by registration number : response should be 200 ok
    @GetMapping("/registration/{registrationNumber}")
    public ResponseEntity<StudentResponseDto> getStudentByRegistrationNumber(
            @PathVariable String registrationNumber) {

        return ResponseEntity.ok(service.getStudentByRegistrationNumber(registrationNumber));
    }

    // get all subjects of a student by Id : response should be 200 ok
    @GetMapping("/{id}/subjects")
    public ResponseEntity<List<SubjectResponseDto>> getAllSubjectsByStudentId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getSubjectsOfStudentById(id));
    }

    // create new student : response should be 201 created
    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(
            @RequestBody @Valid StudentRequestDto studentRequestDto) {

        StudentResponseDto student = service.createStudent(studentRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.id()).toUri();

        return ResponseEntity.created(location).body(student);
    }

    // assign subject to a student by its id : response should be 200 ok
    @PostMapping("/{studentId}/subjects/{subjectId}")
    public ResponseEntity<StudentResponseDto> assignSubjectToStudent(
            @PathVariable Long studentId,
            @PathVariable Long subjectId
    ) {
        StudentResponseDto student = service.assignSubject(studentId, subjectId);
        return ResponseEntity.ok(student);
    }

    // unassign subject to a student by its id : response should be 200 ok
    @DeleteMapping("/{studentId}/subjects/{subjectId}")
    public ResponseEntity<StudentResponseDto> unAssignSubjectFromStudent(
            @PathVariable Long studentId,
            @PathVariable Long subjectId
    ) {
        StudentResponseDto student = service.unassignSubject(studentId, subjectId);
        return ResponseEntity.ok(student);
    }
}