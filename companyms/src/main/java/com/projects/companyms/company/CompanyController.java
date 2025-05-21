package com.projects.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> findAll(){

        return ResponseEntity.ok(companyService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Company company){

        companyService.createCompany(company);
        return new ResponseEntity<>("Company Created Successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
       Company company=companyService.getCompanyById(id);
        if(company!=null){
            return new ResponseEntity<>(company, HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        Boolean delete=companyService.deleteById(id);
        if(delete){
            return new ResponseEntity<>("Company deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Company updatedCompany){
        Boolean update=companyService.updateCompany(id,updatedCompany);
        if(update){
            return new ResponseEntity<>("Company Updated",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }
}
