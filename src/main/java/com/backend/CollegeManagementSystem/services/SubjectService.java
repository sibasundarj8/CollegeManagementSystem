package com.backend.CollegeManagementSystem.services;

import com.backend.CollegeManagementSystem.dtos.request.SubjectRequestDto;
import com.backend.CollegeManagementSystem.dtos.response.SubjectResponseDto;
import com.backend.CollegeManagementSystem.entities.ProfessorEntity;
import com.backend.CollegeManagementSystem.entities.SubjectEntity;
import com.backend.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.backend.CollegeManagementSystem.repositories.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository repository;
    private final ProfessorService professorService;

    public SubjectService(SubjectRepository repository, ProfessorService professorService) {
        this.repository = repository;
        this.professorService = professorService;
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Helper-Methods⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    private SubjectResponseDto mapToResponse(SubjectEntity subject) {
        Long id = subject.getId();
        String title = subject.getTitle();
        ProfessorEntity professor = subject.getProfessor();
        String professorName = (professor == null) ? null : subject.getProfessor().getTitle();

        return new SubjectResponseDto(id, title, professorName);
    }

    SubjectEntity getSubjectByIdOrThrow(Long id) {
        return repository.findSubjectByIdWithRelations(id).orElseThrow(() ->
                new ResourceNotFoundException("Subject not found with id " + id));
    }

    List<SubjectEntity> findAllSubjectsWithId(List<Long> ids) {
        return repository.findAllById(ids);
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Service-API⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    @Transactional(readOnly = true)
    public List<SubjectResponseDto> getAllSubjects() {
        return repository.findAllSubjects();
    }

    @Transactional(readOnly = true)
    public SubjectResponseDto getSubjectById(Long id) {
        return repository.findSubjectById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + id));
    }

    @Transactional(readOnly = true)
    public SubjectResponseDto getSubjectByTitle(String title){
        return repository.findSubjectByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with title " + title));
    }

    @Transactional
    public SubjectResponseDto createSubject(SubjectRequestDto subjectRequestDto) {
        SubjectEntity subject = new SubjectEntity();

        subject.setTitle(subjectRequestDto.getTitle());

        if ((subjectRequestDto.getProfessorId() != null)) {
            ProfessorEntity professor = professorService.getProfessorByIdElseThrough(subjectRequestDto.getProfessorId());
            subject.setProfessor(professor);
            professor.getSubjects().add(subject);
        }

        subject = repository.save(subject);

        return mapToResponse(subject);
    }

    @Transactional
    public SubjectResponseDto assignProfessor(Long subjectId, Long professorId) {
        SubjectEntity subject = getSubjectByIdOrThrow(subjectId);
        ProfessorEntity professor = professorService.getProfessorByIdElseThrough(professorId);

        subject.setProfessor(professor);
        professor.getSubjects().add(subject);

        subject = repository.save(subject);
        return mapToResponse(subject);
    }

    @Transactional
    public SubjectResponseDto unassignProfessor(Long subjectId, Long professorId) {
        SubjectEntity subject = getSubjectByIdOrThrow(subjectId);
        ProfessorEntity professor = professorService.getProfessorByIdElseThrough(professorId);

        subject.setProfessor(null);
        professor.getSubjects().remove(subject);

        subject = repository.save(subject);
        return mapToResponse(subject);
    }

    @Transactional
    public void deleteSubjectById(Long subjectId) {
        repository.deleteById(subjectId);
    }
}