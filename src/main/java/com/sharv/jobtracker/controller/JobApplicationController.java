package com.sharv.jobtracker.controller;

import com.sharv.jobtracker.dto.JobApplicationRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.sharv.jobtracker.dto.JobApplicationResponseDTO;
import com.sharv.jobtracker.entity.ApplicationStatus;
import com.sharv.jobtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Tag(name = "Job Applications", description = "Endpoints for managing job applications")
public class JobApplicationController {

    private final JobApplicationService service;

    @Operation(summary = "Create a new job application")
    @PostMapping
    public ResponseEntity<JobApplicationResponseDTO> create(
            @Valid @RequestBody JobApplicationRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(dto, userDetails.getUsername()));
    }

    @Operation(summary = "Get all job applications for the logged-in user, paginated")
    @GetMapping
    public ResponseEntity<Page<JobApplicationResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) ApplicationStatus status,
            @AuthenticationPrincipal UserDetails userDetails) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        String username = userDetails.getUsername();

        if (status != null) {
            return ResponseEntity.ok(service.getByStatus(username, status, pageable));
        }
        return ResponseEntity.ok(service.getAll(username, pageable));
    }

    @Operation(summary = "Get job applications for the given ID")
    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> getById(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.getById(id, userDetails.getUsername()));
    }

    @Operation(summary = "Update job applications for the given ID")
    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.update(id, dto, userDetails.getUsername()));
    }

    @Operation(summary = "Delete job applications for the given ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        service.delete(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}