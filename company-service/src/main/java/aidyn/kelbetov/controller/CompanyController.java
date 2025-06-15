package aidyn.kelbetov.controller;

import aidyn.kelbetov.entity.Company;
import aidyn.kelbetov.dto.CompanyDto;
import aidyn.kelbetov.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping("/info/all")
    public Page<CompanyDto> getAllCompany(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue =  "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return service.getAllCompanies(pageable);
    }

    @GetMapping("/info/{companyId}")
    public CompanyDto getCompanyById(@PathVariable Long companyId){
        return service.getCompanyById(companyId);
    }

    @PostMapping("/create")
    public CompanyDto createCompany(@Valid @RequestBody CompanyDto companyDto){
        return service.createCompany(companyDto);
    }

    @PutMapping("/update/{companyId}")
    public CompanyDto editCompany(@Valid @RequestBody CompanyDto companyDto, @PathVariable Long companyId){
        return service.editCompany(companyId, companyDto);
    }

    @DeleteMapping("/delete/{companyId}")
    public void deleteCompany(@PathVariable Long companyId){
        service.deleteCompany(companyId);
    }
}
