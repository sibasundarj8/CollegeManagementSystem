package com.backend.CollegeManagementSystem.repositories;

import com.backend.CollegeManagementSystem.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    /*
     * returns a complete student entity by student_id with initializing relationships eagerly
     */
    @Query("""
                   SELECT DISTINCT s
                   FROM SubjectEntity s
                   LEFT JOIN FETCH s.professor
                   LEFT JOIN FETCH s.students
                   WHERE s.id = :id
            """)
    Optional<SubjectEntity> findSubjectByIdWithRelations(@Param("id") Long id);
}