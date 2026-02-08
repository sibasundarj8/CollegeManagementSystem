package com.backend.CollegeManagementSystem;

import com.backend.CollegeManagementSystem.dtos.AdmissionRecordResponseDto;
import com.backend.CollegeManagementSystem.services.AdmissionRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AdmissionRecordTests {

    @Autowired
    private AdmissionRecordService admissionRecordService;

    @Test
    void getAllAdmissionRecordsTest() {
        List<AdmissionRecordResponseDto> records = admissionRecordService.getAllRecords();

        for (AdmissionRecordResponseDto record : records) {
            System.out.println(record);
        }
    }

    @Test
    void getAdmissionRecordByIdTest() {
        AdmissionRecordResponseDto record = admissionRecordService.getRecordById(1L);
        System.out.println(record);
    }

    @Test
    void updateFeesTest() {
        boolean updateFees = admissionRecordService.updateFees(1L, 5000);
        System.out.println(updateFees);
    }
}
