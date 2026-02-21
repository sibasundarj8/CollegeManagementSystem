package com.backend.CollegeManagementSystem.repositories;

import com.backend.CollegeManagementSystem.dtos.flat.ProfessorFlatDto;
import com.backend.CollegeManagementSystem.dtos.response.ProfessorResponseDto;
import com.backend.CollegeManagementSystem.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    /*
     * returns a professor by id using projection
     */
    @Query("""
                   SELECT DISTINCT new com.backend.CollegeManagementSystem.dtos.flat.ProfessorFlatDto(
                               p.id,
                               p.title,
                               sub.title
                   )
                   FROM ProfessorEntity p
                   INNER JOIN p.subjects sub
            """)
    Optional<List<ProfessorFlatDto>> findProfessorById(@Param("id") Long id);
}