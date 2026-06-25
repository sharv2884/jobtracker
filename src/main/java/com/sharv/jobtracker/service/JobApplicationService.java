package com.sharv.jobtracker.service;

import com.sharv.jobtracker.dto.JobApplicationRequestDTO;
import com.sharv.jobtracker.dto.JobApplicationResponseDTO;
import com.sharv.jobtracker.entity.ApplicationStatus;
import com.sharv.jobtracker.entity.JobApplication;
import com.sharv.jobtracker.entity.User;
import com.sharv.jobtracker.exception.ResourceNotFoundException;
import com.sharv.jobtracker.repository.JobApplicationRepository;
import com.sharv.jobtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository repository;
    private final UserRepository userRepository;

    public JobApplicationResponseDTO create(JobApplicationRequestDTO dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        JobApplication app = new JobApplication();
        app.setCompanyName(dto.getCompanyName());
        app.setJobTitle(dto.getJobTitle());
        app.setStatus(dto.getStatus());
        app.setAppliedDate(dto.getAppliedDate());
        app.setNotes(dto.getNotes());
        app.setUser(user);

        return toResponse(repository.save(app));
    }

    public Page<JobApplicationResponseDTO> getAll(String username, Pageable pageable) {
        return repository.findByUserUsername(username, pageable).map(this::toResponse);
    }

    public Page<JobApplicationResponseDTO> getByStatus(String username, ApplicationStatus status, Pageable pageable) {
        return repository.findByUserUsernameAndStatus(username, status, pageable).map(this::toResponse);
    }

    public JobApplicationResponseDTO getById(Long id, String username) {
        JobApplication app = repository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        return toResponse(app);
    }

    public JobApplicationResponseDTO update(Long id, JobApplicationRequestDTO dto, String username) {
        JobApplication app = repository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));

        app.setCompanyName(dto.getCompanyName());
        app.setJobTitle(dto.getJobTitle());
        app.setStatus(dto.getStatus());
        app.setAppliedDate(dto.getAppliedDate());
        app.setNotes(dto.getNotes());

        return toResponse(repository.save(app));
    }

    public void delete(Long id, String username) {
        JobApplication app = repository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        repository.delete(app);
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