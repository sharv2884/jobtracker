package com.sharv.jobtracker.dto;

import com.sharv.jobtracker.entity.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class JobApplicationRequestDTO {

    @Schema(description = "Name of the company", example = "Google")
    @NotBlank(message = "Company name is required")
    private String companyName;

    @Schema(description = "Job title applied for", example = "Backend Engineer")
    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @Schema(description = "Current status of the application")
    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    @Schema(description = "Date the application was submitted", example = "2026-06-11")
    private LocalDate appliedDate;

    @Schema(description = "Optional notes about this application")
    private String notes;
}