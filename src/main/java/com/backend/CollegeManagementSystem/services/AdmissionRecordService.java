package com.backend.CollegeManagementSystem.services;

import com.backend.CollegeManagementSystem.dtos.response.AdmissionRecordResponseDto;
import com.backend.CollegeManagementSystem.entities.AdmissionRecordEntity;
import com.backend.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.backend.CollegeManagementSystem.repositories.AdmissionRecordRepository;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmissionRecordService {
    private final AdmissionRecordRepository repository;
    private final ModelMapper modelMapper;

    public AdmissionRecordService(AdmissionRecordRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    // private method, which returns the record entity by id or throws ResourceNotFoundException if record does not exist.
    @Transactional(readOnly = true)
    private AdmissionRecordEntity getRecordByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Admission record with id " + id + " not found!"));
    }

    // returns all the Admission records
    @Transactional(readOnly = true)
    public List<AdmissionRecordResponseDto> getAllRecords() {
        return repository.findAllRecords();
    }

    // returns a particular students record by Id
    @Transactional(readOnly = true)
    public AdmissionRecordResponseDto getRecordById(Long id) {
        AdmissionRecordEntity recordEntity = getRecordByIdOrThrow(id);

        return modelMapper.map(recordEntity, AdmissionRecordResponseDto.class);
    }

    // update fees --> no need to load the whole student entity, we just need to update the fees by id.
    @Transactional
    public boolean updateFees(Long id, Integer fees) {
        int rowAffected = repository.updateFees(id, fees);
        if (rowAffected == 0) throw new ResourceNotFoundException("Admission record with id " + id + " not found!");

        return true;
    }
}