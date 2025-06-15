package aidyn.kelbetov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private Long id;
    @NotBlank(message = "Company name must be not blank!")
    @Size(min = 2, message = "Company name size must be al least 2 characters!")
    private String companyName;
    private CustomPage<CustomerDto> customers;
}
