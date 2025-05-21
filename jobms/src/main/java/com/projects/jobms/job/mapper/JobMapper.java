package com.projects.jobms.job.mapper;

import com.projects.jobms.job.Job;
import com.projects.jobms.job.dto.JobDTO;
import com.projects.jobms.job.external.Company;
import com.projects.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews){
        JobDTO jobDTO =new JobDTO();
        jobDTO.setCompany(company);
        jobDTO.setId(job.getId());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setReviews(reviews);
        return jobDTO;
    }
}
