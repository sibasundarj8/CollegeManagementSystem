package com.backend.CollegeManagementSystem.repositories;

import com.backend.CollegeManagementSystem.entities.AdmissionRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecordEntity, Long> {
}