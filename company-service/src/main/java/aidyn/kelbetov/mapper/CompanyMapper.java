package aidyn.kelbetov.mapper;

import aidyn.kelbetov.dto.CompanyDto;
import aidyn.kelbetov.entity.Company;

public class CompanyMapper {
    public static CompanyDto toDto(Company company){
        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setCompanyName(company.getCompanyName());
        return dto;
    }

    public static Company toEntity(CompanyDto dto){
        Company company = new Company();
        company.setCompanyName(dto.getCompanyName());
        return company;
    }
}
