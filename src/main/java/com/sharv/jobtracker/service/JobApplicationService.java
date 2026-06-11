package com.sharv.jobtracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sharv.jobtracker.dto.JobApplicationRequestDTO;
import com.sharv.jobtracker.dto.JobApplicationResponseDTO;
import com.sharv.jobtracker.entity.ApplicationStatus;
import com.sharv.jobtracker.entity.JobApplication;
import com.sharv.jobtracker.exception.ResourceNotFoundException;
import com.sharv.jobtracker.repository.JobApplicationRepository;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository repository = null;

    public JobApplicationResponseDTO create(JobApplicationRequestDTO dto) {
        JobApplication app = new JobApplication();
        app.setCompanyName(dto.getCompanyName());
        app.setJobTitle(dto.getJobTitle());
        app.setStatus(dto.getStatus());
        app.setAppliedDate(dto.getAppliedDate());
        app.setNotes(dto.getNotes());
        return toResponse(repository.save(app));
    }

    public Page<JobApplicationResponseDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public Page<JobApplicationResponseDTO> getByStatus(ApplicationStatus status, Pageable pageable) {
        return repository.findByStatus(status, pageable).map(this::toResponse);
    }

    public JobApplicationResponseDTO getById(Long id) {
        JobApplication app = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        return toResponse(app);
    }

    public JobApplicationResponseDTO update(Long id, JobApplicationRequestDTO dto) {
        JobApplication app = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        app.setCompanyName(dto.getCompanyName());
        app.setJobTitle(dto.getJobTitle());
        app.setStatus(dto.getStatus());
        app.setAppliedDate(dto.getAppliedDate());
        app.setNotes(dto.getNotes());
        return toResponse(repository.save(app));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Job application not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private JobApplicationResponseDTO toResponse(JobApplication app) {
        JobApplicationResponseDTO dto = new JobApplicationResponseDTO();
        dto.setId(app.getId());
        dto.setCompanyName(app.getCompanyName());
        dto.setJobTitle(app.getJobTitle());
        dto.setStatus(app.getStatus());
        dto.setAppliedDate(app.getAppliedDate());
        dto.setNotes(app.getNotes());
        dto.setCreatedAt(app.getCreatedAt());
        dto.setUpdatedAt(app.getUpdatedAt());
        return dto;
    }
}