package com.backend.CollegeManagementSystem.controllers;

import com.backend.CollegeManagementSystem.services.ProfessorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }


}