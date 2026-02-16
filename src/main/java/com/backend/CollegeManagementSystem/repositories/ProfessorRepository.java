package com.backend.CollegeManagementSystem.repositories;

import com.backend.CollegeManagementSystem.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {

    /*
     * returns a complete professor entity by id with initializing relationships eagerly
     */
    @Query("""
                   SELECT DISTINCT p
                   FROM ProfessorEntity p
                   LEFT JOIN FETCH p.subjects
                   WHERE p.id = :id
            """)
    Optional<ProfessorEntity> findSubjectByIdWithRelations(@Param("id") Long id);
}