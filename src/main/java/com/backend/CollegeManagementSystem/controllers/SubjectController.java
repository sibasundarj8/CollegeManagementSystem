package com.backend.CollegeManagementSystem.controllers;

import com.backend.CollegeManagementSystem.services.SubjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService service;

    public SubjectController(SubjectService service) {
        this.service = service;
    }


}