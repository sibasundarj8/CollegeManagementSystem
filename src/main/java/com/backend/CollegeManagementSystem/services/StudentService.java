package com.backend.CollegeManagementSystem.services;

import com.backend.CollegeManagementSystem.dtos.flat.StudentFlatDto;
import com.backend.CollegeManagementSystem.dtos.response.StudentResponseDto;
import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.entities.ProfessorEntity;
import com.backend.CollegeManagementSystem.entities.StudentEntity;
import com.backend.CollegeManagementSystem.entities.SubjectEntity;
import com.backend.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.backend.CollegeManagementSystem.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;
    private final ModelMapper mapper;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.repository = studentRepository;
        this.mapper = modelMapper;
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Helper-Methods⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    private StudentResponseDto mapToResponseDto(StudentEntity student) {
        Long studentId = student.getId();
        String name = student.getName();
        String registrationNumber = student.getRegistrationNumber();

        Set<String> subjects = student.getSubjects().stream()
                .map(SubjectEntity::getTitle)
                .collect(Collectors.toSet());

        Set<String> professors = student.getProfessors().stream()
                .map(ProfessorEntity::getTitle)
                .collect(Collectors.toSet());

        return new StudentResponseDto(studentId, name, registrationNumber, subjects, professors);
    }

    // private method to return student entity by id or throw ResourceNotFoundException if student does not exist.
    @Transactional(readOnly = true)
    private StudentEntity getStudentByIdOrThrow(Long id) {
        return repository.findStudentByIdWithRelations(id).orElseThrow(() ->
                new ResourceNotFoundException("Student with id " + id + " not found!"));
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Service-API⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    // get all students
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getAllStudents() {
        List<StudentFlatDto> studentFlatRows = repository.findAllStudentsFlat();
        Map<Long, StudentResponseDto> map = new HashMap<>();

        for (StudentFlatDto row : studentFlatRows) {
            StudentResponseDto student = map.computeIfAbsent(row.id(), k -> new StudentResponseDto(
                    k,
                    row.name(),
                    row.registrationNumber(),
                    new HashSet<>(),
                    new HashSet<>()
            ));

            if (row.subject() != null) student.subjects().add(row.subject());
            if (row.professor() != null) student.professors().add(row.professor());
        }

        return (List<StudentResponseDto>) map.values();
    }

    // get students By id
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(Long id) {
        StudentEntity student = getStudentByIdOrThrow(id);
        return mapToResponseDto(student);
    }

    // get all students by name
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getStudentsByName(String name) {
        List<StudentEntity> students = repository.findStudentsByNameWithRelations(name);

        return students.stream()
                .map(this::mapToResponseDto)
                .distinct()
                .toList();
    }

    // get the student with given registration number
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentsByRegistrationNumber(String registrationNumber) {
        StudentEntity student = repository.findStudentByRegistrationNumber(registrationNumber);
        if (student == null) throw new ResourceNotFoundException("Student with registration number " + registrationNumber + " not found!");

        return mapToResponseDto(student);
    }

    /*
     * get all the subjects of a particular student by Id.
     */
    @Transactional(readOnly = true)
    public List<SubjectResponseDto> getSubjectsOfStudentById(Long id) {
        return repository.getSubjectsByStudentId(id);
    }
}