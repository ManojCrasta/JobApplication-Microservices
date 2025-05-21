package com.projects.companyms.company;

import com.projects.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();
    void createCompany(Company company);

    Company getCompanyById(Long id);

    Boolean deleteById(Long id);

    Boolean updateCompany(Long id, Company updatedCompany);

    void updateCompanyRating(ReviewMessage reviewMessage);
}
