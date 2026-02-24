package com.backend.CollegeManagementSystem.controllers;

import com.backend.CollegeManagementSystem.dtos.request.ProfessorRequestDto;
import com.backend.CollegeManagementSystem.dtos.response.ProfessorResponseDto;
import com.backend.CollegeManagementSystem.services.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDto> getProfessorById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProfessorById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDto>> getProfessorByTitle(
            @RequestParam(name = "name", required = false) String title) {
        if (title == null || title.isBlank()) {
            return ResponseEntity.ok(service.getAllProfessors());
        }
        return ResponseEntity.ok(service.getAllProfessorsByTitle(title));
    }

    @GetMapping("/{id}/subjects")
    public ResponseEntity<List<String>> getAllSubjectsOfProfessor(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAllSubjectsOfProfessor(id));
    }

    @PostMapping
    public ResponseEntity<ProfessorResponseDto> createProfessor(@RequestBody @Valid ProfessorRequestDto professorRequestDto) {
        ProfessorResponseDto professor = service.createProfessor(professorRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(professor.id()).toUri();

        return ResponseEntity.created(location).body(professor);
    }

}