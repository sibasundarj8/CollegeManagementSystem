package com.backend.CollegeManagementSystem.services;

import com.backend.CollegeManagementSystem.entities.SubjectEntity;
import com.backend.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.backend.CollegeManagementSystem.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Helper-Methods⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    SubjectEntity getSubjectByIdOrThrow(Long id) {
        return repository.findSubjectByIdWithRelations(id).orElseThrow(() ->
                new ResourceNotFoundException("Subject not found with id " + id));
    }

/*⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯Service-API⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯*/

    public List<SubjectEntity> findAllSubjectsWithId(List<Long> ids) {
        return repository.findAllById(ids);
    }
}