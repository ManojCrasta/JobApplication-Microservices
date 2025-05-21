package com.projects.jobms.job;

import com.projects.jobms.job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> findAll(){

        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping("/jobs")
    public ResponseEntity<String> createJob(@RequestBody Job job){

        jobService.createJob(job);
        return new ResponseEntity<>("Job Created Successfully",HttpStatus.OK);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobDTO jobDTO =jobService.getJobByid(id);
        if(jobDTO !=null){
            return new ResponseEntity<>(jobDTO, HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        Boolean delete=jobService.deleteById(id);
        if(delete){
           return new ResponseEntity<>("Job deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/jobs/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob){
        Boolean update=jobService.updateJob(id,updatedJob);
        if(update){
            return new ResponseEntity<>("Job Updated",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }
}
