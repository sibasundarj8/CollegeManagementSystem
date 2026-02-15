package com.backend.CollegeManagementSystem.controllers;

import com.backend.CollegeManagementSystem.services.AdmissionRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admission_records")
public class AdmissionRecordController {
    private final AdmissionRecordService service;

    public AdmissionRecordController(AdmissionRecordService service) {
        this.service = service;
    }


}