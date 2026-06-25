package com.sharv.jobtracker.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sharv.jobtracker.entity.ApplicationStatus;
import com.sharv.jobtracker.entity.JobApplication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findByStatus(ApplicationStatus status, Pageable pageable);

    Page<JobApplication> findByCompanyNameContainingIgnoreCase(String companyName, Pageable pageable);
    Page<JobApplication> findByUserUsername(String username, Pageable pageable);
    Page<JobApplication> findByUserUsernameAndStatus(String username, ApplicationStatus status, Pageable pageable);
    Optional<JobApplication> findByIdAndUserUsername(Long id, String username);
}