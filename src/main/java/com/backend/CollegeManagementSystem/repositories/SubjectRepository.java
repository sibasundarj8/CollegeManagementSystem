package com.backend.CollegeManagementSystem.repositories;

import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    /*
     * returns a complete subject entity by id with initializing relationships eagerly
     */
    @Query("""
                   SELECT DISTINCT s
                   FROM SubjectEntity s
                   LEFT JOIN FETCH s.professor
                   LEFT JOIN FETCH s.students
                   WHERE s.id = :id
            """)
    Optional<SubjectEntity> findSubjectByIdWithRelations(@Param("id") Long id);

    /*
     * returns List of all subject by using projection
     */
    @Query("""
                   SELECT new com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto(
                               s.id, s.title, p.title
                   )
                   FROM SubjectEntity s
                   LEFT JOIN s.professor p
            """)
    List<SubjectResponseDto> findAllSubjects();

    /*
     * returns a subject by subject id using projection
     */
    @Query("""
                   SELECT new com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto(
                               s.id, s.title, p.title
                   )
                   FROM SubjectEntity s
                   LEFT JOIN s.professor p
                   WHERE s.id = :id
            """)
    Optional<SubjectResponseDto> findSubjectById(@Param("id") Long id);

    /*
     * returns a subject by subject title using projection
     */
    @Query("""
                   SELECT new com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto(
                               s.id, s.title, p.title
                   )
                   FROM SubjectEntity s
                   LEFT JOIN s.professor p
                   WHERE s.title = :title
            """)
    Optional<SubjectResponseDto> findSubjectByTitle(@Param("title") String title);
}