package com.sharv.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

import com.sharv.jobtracker.entity.ApplicationStatus;

@Data
public class JobApplicationRequestDTO {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    private LocalDate appliedDate;

    private String notes;
}