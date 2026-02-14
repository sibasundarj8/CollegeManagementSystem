package com.backend.CollegeManagementSystem.services;

import com.backend.CollegeManagementSystem.dtos.flat.StudentFlatDto;
import com.backend.CollegeManagementSystem.dtos.response.StudentResponseDto;
import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.entities.StudentEntity;
import com.backend.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.backend.CollegeManagementSystem.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StudentService {
    private final StudentRepository repository;
    private final ModelMapper mapper;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.repository = studentRepository;
        this.mapper = modelMapper;
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Helper-Methods⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    private List<StudentResponseDto> mapToResponseDto(List<StudentFlatDto> joinTable) {
        Map<Long, StudentResponseDto> map = new HashMap<>();

        for (StudentFlatDto row : joinTable) {
            StudentResponseDto student = map.computeIfAbsent(row.id(), k -> new StudentResponseDto(
                    k,
                    row.name(),
                    row.registrationNumber(),
                    new HashSet<>()
            ));

            if (row.subjectId() != null) {
                student.subjects().add(
                        new SubjectResponseDto(
                                row.subjectId(),
                                row.subjectTitle(),
                                row.professorName()
                        )
                );
            }
        }

        return new ArrayList<>(map.values());
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
        return mapToResponseDto(studentFlatRows);
    }

    // get students By id
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(Long id) {
        List<StudentFlatDto> student = repository.findStudentFlatById(id);

        if (student == null || student.isEmpty()) {
            throw new ResourceNotFoundException("Student with id " + id + " not found!");
        }

        return mapToResponseDto(student).getFirst();
    }

    // get all students by name
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getStudentsByName(String name) {
        List<StudentFlatDto> students = repository.findStudentsFlatByName(name);

        if (students == null || students.isEmpty()) {
            throw new ResourceNotFoundException("Student with name " + name + " not found!");
        }

        return mapToResponseDto(students);
    }

    // get the student with given registration number
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentsByRegistrationNumber(String registrationNumber) {
        List<StudentFlatDto> student = repository.findStudentFlatByRegistrationNumber(registrationNumber);

        if (student == null || student.isEmpty()) {
            throw new ResourceNotFoundException("Student with registration number " + registrationNumber + " not found!");
        }

        return mapToResponseDto(student).getFirst();
    }

    /*
     * get all the subjects of a particular student by Id.
     */
    @Transactional(readOnly = true)
    public List<SubjectResponseDto> getSubjectsOfStudentById(Long id) {
        List<SubjectResponseDto> subjects = repository.getSubjectsByStudentId(id);

        if (subjects.isEmpty()) {
            if (!repository.existsById(id)) throw new ResourceNotFoundException("Student with id " + id + " does not exist!");
        }

        return subjects;
    }
}