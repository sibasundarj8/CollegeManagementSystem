package com.backend.CollegeManagementSystem.repositories;

import com.backend.CollegeManagementSystem.dtos.flat.StudentFlatDto;
import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    /*
     * get list of all students flat data means no multiple complex begs in rows.
     */
    @Query("""
                   SELECT new com.backend.CollegeManagementSystem.dtos.flat.StudentFlatDto(
                       s.id, s.name, s.registrationNumber, sub.id, sub.title, p.title
                   )
                   FROM StudentEntity s
                   LEFT JOIN s.subjects sub
                   LEFT JOIN sub.professor p
            """)
    List<StudentFlatDto> findAllStudentsFlat();

    /*
     * get list of all students flat data means no multiple complex begs in rows.
     */
    @Query("""
                   SELECT new com.backend.CollegeManagementSystem.dtos.flat.StudentFlatDto(
                       s.id, s.name, s.registrationNumber, sub.id, sub.title, p.title
                   )
                   FROM StudentEntity s
                   LEFT JOIN s.subjects sub
                   LEFT JOIN sub.professor p
                   WHERE s.id = :id
            """)
    List<StudentFlatDto> findStudentFlatById(@Param("id") Long id);

    /*
     * returns all students complete entity with particular name by initializing relationships eagerly
     */
    @Query("""
                   SELECT new com.backend.CollegeManagementSystem.dtos.flat.StudentFlatDto(
                       s.id, s.name, s.registrationNumber, sub.id, sub.title, p.title
                   )
                   FROM StudentEntity s
                   LEFT JOIN s.subjects sub
                   LEFT JOIN sub.professor p
                   WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))
            """)
    // if name = "siba than "CONCAT('%', :name, '%')" means "%siba%", in SQL it means matches every string containing 'siba'.
    List<StudentFlatDto> findStudentsFlatByName(@Param("name") String name);

    /*
     * return the student entity with given registration number
     */
    @Query("""
                   SELECT new com.backend.CollegeManagementSystem.dtos.flat.StudentFlatDto(
                       s.id, s.name, s.registrationNumber, sub.id, sub.title, p.title
                   )
                   FROM StudentEntity s
                   LEFT JOIN s.subjects sub
                   LEFT JOIN sub.professor p
                   WHERE s.registrationNumber = :registrationNumber
            """)
    List<StudentFlatDto> findStudentFlatByRegistrationNumber(@Param("registrationNumber") String registrationNumber);

    /*
     * returns a complete student entity by student_id with initializing relationships eagerly
     */
    @Query("""
                   SELECT DISTINCT s
                   FROM StudentEntity s
                   LEFT JOIN FETCH s.subjects sub
                   LEFT JOIN FETCH sub.professor
                   WHERE s.id = :id
            """)
    Optional<StudentEntity> findStudentByIdWithRelations(@Param("id") Long id);

    /*
     * get all the subjects of a particular student by its Id.
     */
    @Query("""
                   SELECT new com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto(
                       sub.id,
                       sub.title,
                       prof.title
                   )
                   FROM StudentEntity s
                   JOIN s.subjects sub
                   JOIN sub.professor prof
                   WHERE s.id = :id
            """)
    List<SubjectResponseDto> findSubjectsByStudentId(@Param("id") Long id);
}