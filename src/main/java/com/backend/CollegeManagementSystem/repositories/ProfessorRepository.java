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
     * returns all professors using projection
     */
    @Query("""
                   SELECT DISTINCT new com.backend.CollegeManagementSystem.dtos.flat.ProfessorFlatDto(
                               p.id,
                               p.title,
                               sub.title
                   )
                   FROM ProfessorEntity p
                   LEFT JOIN p.subjects sub
            """)
    List<ProfessorFlatDto> findAllFlatProfessors();

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
                   LEFT JOIN p.subjects sub
                   WHERE p.id = :id
            """)
    List<ProfessorFlatDto> findProfessorFlatById(@Param("id") Long id);

    /*
     * returns all the professors which matches the title
     */
    @Query("""
                   SELECT DISTINCT new com.backend.CollegeManagementSystem.dtos.flat.ProfessorFlatDto(
                               p.id,
                               p.title,
                               sub.title
                   )
                   FROM ProfessorEntity p
                   LEFT JOIN p.subjects sub
                   WHERE LOWER(p.title) LIKE CONCAT('%', :title, '%')
            """)
    List<ProfessorFlatDto> findProfessorFlatByTitle(@Param("title") String title);

    /*
     * returns all the subjects of particular professor by professor_id
     */
    @Query("""
                   SELECT sub.title
                   FROM ProfessorEntity p
                   LEFT JOIN p.subjects sub
                   WHERE p.id = :id
            """)
    List<String> findAllSubjectOfProfessor(Long id);
}