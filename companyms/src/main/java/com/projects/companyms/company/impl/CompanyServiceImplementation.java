package com.projects.companyms.company.impl;

import com.projects.companyms.company.Company;
import com.projects.companyms.company.CompanyRepository;
import com.projects.companyms.company.CompanyService;
import com.projects.companyms.company.clients.ReviewClient;
import com.projects.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImplementation implements CompanyService {
    CompanyRepository companyRepository;

    ReviewClient reviewClient;

    public CompanyServiceImplementation(CompanyRepository companyRepository,
                                        ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient=reviewClient;
    }

    private Long nextId=1L;
    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public void createCompany(Company company) {
        try {
            companyRepository.save(company);
        } catch (StaleObjectStateException e) {
            // Handle concurrency conflict (e.g., refresh entity)
            System.out.println("Concurrency issue: " + e.getMessage());
        }
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }


    @Override
    public Boolean deleteById(Long id) {

        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateCompany(Long id, Company updatedcompany) {
        Optional<Company> companyOptional=companyRepository.findById(id);

        if(companyOptional.isPresent()){
            Company company=companyOptional.get();
            company.setDescription(updatedcompany.getDescription());
            company.setName(updatedcompany.getName());

            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {

        System.out.println(reviewMessage.getDescription());
        Company company=companyRepository.findById(reviewMessage.getCompanyId())
        .orElseThrow(() -> new NotFoundException("Company Not Found"+reviewMessage.getCompanyId()));
        double averageRating=reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }
}
