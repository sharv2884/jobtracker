package com.sharv.jobtracker.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sharv.jobtracker.entity.ApplicationStatus;

@Data
public class JobApplicationResponseDTO {
    private Long id;
    private String companyName;
    private String jobTitle;
    private ApplicationStatus status;
    private LocalDate appliedDate;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}