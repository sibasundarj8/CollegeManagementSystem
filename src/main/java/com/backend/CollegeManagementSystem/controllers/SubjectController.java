package com.backend.CollegeManagementSystem.controllers;

import com.backend.CollegeManagementSystem.dtos.request.SubjectRequestDto;
import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.services.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService service;

    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDto> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSubjectById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> getSubjectsWithTitle(
            @RequestParam(value = "title", required = false) String title) {

        List<SubjectResponseDto> subjects = (title == null || title.isBlank()) ?
                service.getAllSubjects() :
                service.getSubjectByTitle(title);

        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<String>> getAllSubjectsByStudentId(@PathVariable("id") Long subjectId) {
        return ResponseEntity.ok(service.getStudentsBySubjectId(subjectId));
    }

    @PostMapping
    public ResponseEntity<SubjectResponseDto> createSubject(@RequestBody SubjectRequestDto subjectRequestDto) {
        SubjectResponseDto subject = service.createSubject(subjectRequestDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(subject.id())
                .toUri();

        return ResponseEntity.created(uri).body(subject);
    }

    @PutMapping("/{subjectId}/professor/{professorId}")
    public ResponseEntity<SubjectResponseDto> assignProfessor(
            @PathVariable Long subjectId,
            @PathVariable Long professorId) {

        SubjectResponseDto subject = service.assignProfessor(subjectId, professorId);
        return ResponseEntity.ok(subject);
    }

    @DeleteMapping("/{subjectId}/professor")
    public ResponseEntity<SubjectResponseDto> unassignProfessor(
            @PathVariable Long subjectId) {

        SubjectResponseDto subject = service.unassignProfessor(subjectId);

        return ResponseEntity.ok(subject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        service.deleteSubjectById(id);
        return ResponseEntity.noContent().build();
    }
}