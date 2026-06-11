package com.sharv.jobtracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sharv.jobtracker.dto.JobApplicationRequestDTO;
import com.sharv.jobtracker.dto.JobApplicationResponseDTO;
import com.sharv.jobtracker.entity.ApplicationStatus;
import com.sharv.jobtracker.service.JobApplicationService;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService service = new JobApplicationService();

    @PostMapping
    public ResponseEntity<JobApplicationResponseDTO> create(
            @Valid @RequestBody JobApplicationRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<JobApplicationResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) ApplicationStatus status) {

        Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (status != null) {
            return ResponseEntity.ok(service.getByStatus(status, pageable));
        }
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}