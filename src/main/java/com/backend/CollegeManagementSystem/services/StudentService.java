package com.backend.CollegeManagementSystem.services;

import com.backend.CollegeManagementSystem.dtos.flat.StudentFlatDto;
import com.backend.CollegeManagementSystem.dtos.request.StudentRequestDto;
import com.backend.CollegeManagementSystem.dtos.response.StudentResponseDto;
import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.entities.AdmissionRecordEntity;
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
    private final SubjectService subjectService;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper, SubjectService subjectService) {
        this.repository = studentRepository;
        this.mapper = modelMapper;
        this.subjectService = subjectService;
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Helper-Methods⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    // takes a list of flat dtos and converts it into a list of response dtos
    private List<StudentResponseDto>  flatDtosToResponseDto(List<StudentFlatDto> joinTable) {
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

    private StudentResponseDto mapToResponseDto(StudentEntity student) {
        Set<SubjectResponseDto> subjects = new HashSet<>();

        for (SubjectEntity sub : student.getSubjects()) {
            String professor = (sub.getProfessor() == null) ? null : sub.getProfessor().getTitle();
            subjects.add(new SubjectResponseDto(sub.getId(), sub.getTitle(), professor));
        }

        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getRegistrationNumber(),
                subjects
        );
    }

    // private method to return student entity by id or throw ResourceNotFoundException if student does not exist.
    @Transactional(readOnly = true)
    StudentEntity getStudentByIdOrThrow(Long id) {
        return repository.findStudentByIdWithRelations(id).orElseThrow(() ->
                new ResourceNotFoundException("Student with id " + id + " not found!"));
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Service-API⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    // get all students
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getAllStudents() {
        List<StudentFlatDto> studentFlatRows = repository.findAllStudentsFlat();
        return flatDtosToResponseDto(studentFlatRows);
    }

    // get students By id
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(Long id) {
        List<StudentFlatDto> student = repository.findStudentFlatById(id);

        if (student == null || student.isEmpty()) {
            throw new ResourceNotFoundException("Student with id " + id + " not found!");
        }

        return flatDtosToResponseDto(student).getFirst();
    }

    // get students by name
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getStudentsByName(String name) {
        List<StudentFlatDto> students =
                (name == null || name.isBlank()) ? repository.findAllStudentsFlat()
                        : repository.findStudentsFlatByName(name);

        return flatDtosToResponseDto(students);
    }

    // get the student with given registration number
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentByRegistrationNumber(String registrationNumber) {
        List<StudentFlatDto> student = repository.findStudentFlatByRegistrationNumber(registrationNumber);

        if (student == null || student.isEmpty()) {
            throw new ResourceNotFoundException("Student with registration number " + registrationNumber + " not found!");
        }

        return flatDtosToResponseDto(student).getFirst();
    }


    // get all the subjects of a particular student by Id.
    @Transactional(readOnly = true)
    public List<SubjectResponseDto> getSubjectsOfStudentById(Long id) {
        List<SubjectResponseDto> subjects = repository.findSubjectsByStudentId(id);

        if (subjects.isEmpty() && !repository.existsById(id)) {
            throw new ResourceNotFoundException("Student with id " + id + " does not exist!");
        }

        return subjects;
    }

    // add a new Student
    @Transactional
    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        StudentEntity student = new StudentEntity();

        student.setName(requestDto.getName());
        student.setRegistrationNumber(requestDto.getRegistrationNumber());

        if (requestDto.getSubjectIds() != null && !requestDto.getSubjectIds().isEmpty()) {
            List<SubjectEntity> subjects = subjectService.findAllSubjectsWithId(requestDto.getSubjectIds());
            student.getSubjects().addAll(subjects);
        }

        AdmissionRecordEntity admissionRecord = new AdmissionRecordEntity();
        admissionRecord.setStudent(student);
        admissionRecord.setFees(requestDto.getFees());

        student.setAdmissionRecord(admissionRecord);
        repository.save(student);

        return mapToResponseDto(student);
    }

    // assign subject to a student by ids
    @Transactional
    public StudentResponseDto assignSubject(Long studentId, Long subjectId) {
        StudentEntity student = getStudentByIdOrThrow(studentId);
        SubjectEntity subject = subjectService.getSubjectByIdOrThrow(subjectId);

        student.addSubject(subject);

        return mapToResponseDto(student);
    }

    // unassign subject from a student by ids
    @Transactional
    public StudentResponseDto unassignSubject(Long studentId, Long subjectId) {
        StudentEntity student = getStudentByIdOrThrow(studentId);
        SubjectEntity subject = subjectService.getSubjectByIdOrThrow(subjectId);

        student.removeSubject(subject);

        return mapToResponseDto(student);
    }
}