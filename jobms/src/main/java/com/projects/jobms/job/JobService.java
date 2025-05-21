package com.projects.jobms.job;

import com.projects.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);

    JobDTO getJobByid(Long id);

    Boolean deleteById(Long id);

    Boolean updateJob(Long id, Job updatedJob);
}
