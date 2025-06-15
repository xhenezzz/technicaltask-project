package aidyn.kelbetov.service;

import aidyn.kelbetov.entity.Company;
import aidyn.kelbetov.dto.CompanyDto;
import aidyn.kelbetov.mapper.CompanyMapper;
import aidyn.kelbetov.repo.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
        logger.info("CompanyService initialized success!");
    }

    public CompanyDto createCompany(CompanyDto companyDto){
        logger.info("Creating new company with name: {}", companyDto.getCompanyName());
        try {
            Company company = CompanyMapper.toEntity(companyDto);
            Company saved = repository.save(company);
            logger.info("Company created successfully with ID:  {} and name: {}",
                    saved.getId(), saved.getCompanyName());
            return CompanyMapper.toDto(saved);
        } catch (Exception e){
            logger.error("Failed to create Company with name {}. Error {}",
                    companyDto.getCompanyName(), e.getMessage(), e);
            throw e;
        }
    }

    public CompanyDto editCompany(Long companyId, CompanyDto companyDto) {
        logger.info("Updating company with ID: {}", companyId);
        logger.debug("Update data: {}", companyDto);

        try {
            Company existingCompany = validateAndGetCompany(companyId);
            String oldName = existingCompany.getCompanyName();

            if (companyDto.getCompanyName() != null) {
                existingCompany.setCompanyName(companyDto.getCompanyName());
                logger.info("Company name updated from '{}' to '{}'", oldName, companyDto.getCompanyName());
            }

            Company saved = repository.save(existingCompany);
            logger.info("Company updated successfully with ID: {}", saved.getId());
            return CompanyMapper.toDto(saved);

        } catch (Exception e) {
            logger.error("Failed to update Company with ID {}. Error: {}", companyId, e.getMessage(), e);
            throw e;
        }
    }

    public CompanyDto getCompanyById(Long companyId) {
        logger.info("Fetching company with ID: {}", companyId);
        Company company = validateAndGetCompany(companyId);
        return CompanyMapper.toDto(company);
    }

    public void deleteCompany(Long companyId) {
        logger.info("Deleting company with ID: {}", companyId);
        Company deletedCompany = validateAndGetCompany(companyId);
        repository.delete(deletedCompany);
        logger.info("Company deleted successfully with ID: {}", companyId);
    }

    public Page<CompanyDto> getAllCompanies(Pageable pageable) { // Добавлен generic тип
        logger.info("Fetching all companies with pagination");
        Page<Company> companies = repository.findAll(pageable); // Добавлен generic тип
        return companies.map(CompanyMapper::toDto);
    }

    private Company validateAndGetCompany(Long companyId) {
        return repository.findById(companyId)
                .orElseThrow(() -> {
                    logger.warn("Company not found with ID: {}", companyId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found!");
                });
    }
}
