package aidyn.kelbetov.controller;

import aidyn.kelbetov.entity.Company;
import aidyn.kelbetov.entity.CompanyDto;
import aidyn.kelbetov.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Company> getAllCompany(){
        return service.getAllCompany();
    }

    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDto companyDto){
        Company company = service.createCompany(companyDto);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/edit/{companyId}")
    public ResponseEntity<Company> editCompany(@RequestBody CompanyDto companyDto, @PathVariable Long companyId){
        Company company =  service.editCompany(companyId, companyDto);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/delete/{companyId}")
    public void deleteCompany(@PathVariable Long companyId){
        service.deleteCompany(companyId);
    }
}
