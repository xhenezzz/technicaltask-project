package aidyn.kelbetov.service;

import aidyn.kelbetov.entity.Company;
import aidyn.kelbetov.entity.CompanyDto;
import aidyn.kelbetov.repo.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public Company createCompany(CompanyDto companyDto){
        Company company = new Company();
        company.setCompanyName(companyDto.getCompanyName());
        return repository.save(company);
    }

    public Company editCompany(Long companyId, CompanyDto companyDto){
        Company company = repository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found!"));

        company.setCompanyName(companyDto.getCompanyName());
        return repository.save(company);
    }

    public void deleteCompany(Long companyId){
        Company company = repository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found!"));

        repository.delete(company);
    }

    public List<Company> getAllCompany(){
        return repository.findAll();
    }
}
