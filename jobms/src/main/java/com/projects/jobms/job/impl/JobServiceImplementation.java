package com.projects.jobms.job.impl;

import com.projects.jobms.job.Job;
import com.projects.jobms.job.JobRepository;
import com.projects.jobms.job.JobService;
import com.projects.jobms.job.client.CompanyClient;
import com.projects.jobms.job.client.ReviewClient;
import com.projects.jobms.job.dto.JobDTO;
import com.projects.jobms.job.external.Company;
import com.projects.jobms.job.external.Review;
import com.projects.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class JobServiceImplementation implements JobService {


    JobRepository jobRepository;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    int attempt=0;

    @Autowired
    RestTemplate restTemplate;

    public JobServiceImplementation(JobRepository jobRepository,
                                    CompanyClient companyClient,
                                    ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    private Long nextId=1L;
    @Override
    //@CircuitBreaker(name = "companyBreaker",
    //        fallbackMethod = "companyBreakerFallbackMethod")
//    @Retry(name = "companyBreaker",
//            fallbackMethod = "companyBreakerFallbackMethod")
    @RateLimiter(name = "companyBreaker",
            fallbackMethod = "companyBreakerFallbackMethod")
    public List<JobDTO> findAll() {

        System.out.println("Attempt=="+ ++attempt);
        List<Job> jobs = jobRepository.findAll();

        List<JobDTO> jobDTOS =new ArrayList<>();

        //RestTemplate restTemplate=new RestTemplate();

        for (Job job:jobs
             ) {
            JobDTO jobDTO = convertToDto(job);
            jobDTOS.add(jobDTO);

        }

        return jobDTOS;
    }
    public List<String> companyBreakerFallbackMethod(Exception e){
        List<String> list= new ArrayList<>();
        list.add("Dummy");
        return list;
    }

    public JobDTO convertToDto(Job job){
        Company company=companyClient.getCompany(job.getCompanyId());
//        ResponseEntity<List<Review>> reviewResponse=restTemplate.exchange(
//                "http://review-service:8083/reviews?companyId="+job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>(){}
//        );
//        List<Review> reviews=reviewResponse.getBody();

        List<Review> reviews=reviewClient.getReviews(job.getCompanyId());
        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job,company,reviews);
        return jobDTO;
    }

    @Override
    public void createJob(Job job) {
        try {
            jobRepository.save(job);
        } catch (StaleObjectStateException e) {
            System.out.println("Concurrency issue: " + e.getMessage());
        }
    }

    @Override
    public JobDTO getJobByid(Long id) {

        Job job= jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }

    @Override
    public Boolean deleteById(Long id) {

        try{
            jobRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional=jobRepository.findById(id);

        if(jobOptional.isPresent()){
                Job job=jobOptional.get();
                job.setDescription(updatedJob.getDescription());
                job.setLocation(updatedJob.getLocation());
                job.setTitle(updatedJob.getTitle());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setMinSalary(updatedJob.getMinSalary());
                jobRepository.save(job);
                return true;
            }
        return false;
    }
}
