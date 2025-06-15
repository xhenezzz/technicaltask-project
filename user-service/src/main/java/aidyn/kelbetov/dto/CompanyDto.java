package aidyn.kelbetov.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CompanyDto {
    private Long id;
    private String companyName;
    private CustomPage<CustomerDto> companies;
}
