package com.backend.CollegeManagementSystem.services;

import com.backend.CollegeManagementSystem.dtos.flat.ProfessorFlatDto;
import com.backend.CollegeManagementSystem.dtos.request.ProfessorRequestDto;
import com.backend.CollegeManagementSystem.dtos.response.ProfessorResponseDto;
import com.backend.CollegeManagementSystem.entities.ProfessorEntity;
import com.backend.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.backend.CollegeManagementSystem.repositories.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Helper-Methods⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    private List<ProfessorResponseDto> mapFlatToResponse(List<ProfessorFlatDto> joinTable) {
        HashMap<Long, ProfessorResponseDto> map = new HashMap<>();

        for (ProfessorFlatDto row : joinTable) {
            ProfessorResponseDto professor = map.computeIfAbsent(
                    row.id(),
                    id -> new ProfessorResponseDto(
                            id,
                            row.title(),
                            new ArrayList<>()
                    )
            );

            String subject = row.subject();

            if (subject != null && !subject.isBlank()) {
                professor.subjects().add(subject);
            }
        }

        return new ArrayList<>(map.values());
    }

    @Transactional(readOnly = true)
    ProfessorEntity getProfessorByIdElseThrough(Long id) {
        return repository.findSubjectByIdWithRelations(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found with id: " + id));
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Service-API⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    @Transactional(readOnly = true)
    public List<ProfessorResponseDto> getAllProfessors() {
        List<ProfessorFlatDto> flatProfessors = repository.findAllFlatProfessors();
        return mapFlatToResponse(flatProfessors);
    }

    @Transactional(readOnly = true)
    public ProfessorResponseDto getProfessorById(Long id){
        List<ProfessorFlatDto> flatProfessor = repository.findProfessorFlatById(id);

        if (flatProfessor.isEmpty()) {
            throw new ResourceNotFoundException("Professor not found with id: " + id);
        }

        return mapFlatToResponse(flatProfessor).get(0);
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponseDto> getAllProfessorsByTitle(String title) {
        List<ProfessorFlatDto> flatProfessors = repository.findProfessorFlatByTitle(title.toLowerCase());
        return mapFlatToResponse(flatProfessors);
    }

    @Transactional(readOnly = true)
    public List<String> getAllSubjectsOfProfessor(Long id) {
        return repository.findAllSubjectOfProfessor(id);
    }

    @Transactional
    public ProfessorResponseDto createProfessor(ProfessorRequestDto professorRequest) {
        ProfessorEntity professor = new ProfessorEntity();
        professor.setTitle(professorRequest.getTitle().trim());

        professor = repository.save(professor);

        return new ProfessorResponseDto(professor.getId(), professor.getTitle(), new ArrayList<>());
    }
}