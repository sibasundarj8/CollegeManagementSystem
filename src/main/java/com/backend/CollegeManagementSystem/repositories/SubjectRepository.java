package com.backend.CollegeManagementSystem.repositories;

import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
                   WHERE LOWER(s.title) LIKE LOWER(CONCAT("%", :title, "%"))
            """)
    List<SubjectResponseDto> findSubjectByTitle(@Param("title") String title);

    /*
     * returns all the names of students associated with this subjectId.
     */
    @Query("""
                   SELECT s.name
                   FROM StudentEntity s
                   INNER JOIN s.subjects sub
                   WHERE sub.id = :subjectId
            """)
    List<String> findAllStudentsWithSubjectId(@Param("subjectId") Long subjectId);

    /*
     * destroy the all student relations with this subject
     */
    @Modifying
    @Query(value = """
                   DELETE FROM student_subject_table
                   WHERE subject_id = :subjectId
            """,
            nativeQuery = true
    )
    void deleteStudentMappings(@Param("subjectId") Long subjectId);
}