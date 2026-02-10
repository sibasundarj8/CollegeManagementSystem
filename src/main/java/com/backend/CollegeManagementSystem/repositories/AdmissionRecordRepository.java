package com.backend.CollegeManagementSystem.repositories;

import com.backend.CollegeManagementSystem.dtos.response.AdmissionRecordResponseDto;
import com.backend.CollegeManagementSystem.entities.AdmissionRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecordEntity, Long> {

    /*
     * query to update the fees of a student by his id
     */
    @Modifying
    @Query("""
           UPDATE AdmissionRecordEntity a SET a.fees = :fees WHERE a.id = :id
           """)
    int updateFees(Long id, Integer fees);

    /*
     * query to get all records id, student.name and fees
     */
    @Query("""
           SELECT new com.backend.CollegeManagementSystem.dtos.response.AdmissionRecordResponseDto(
               a.id, a.student.name, a.fees
           )
           from AdmissionRecordEntity a
           """)
    List<AdmissionRecordResponseDto> findAllRecords();
}