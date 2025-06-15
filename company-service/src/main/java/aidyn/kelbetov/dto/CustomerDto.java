package aidyn.kelbetov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomerDto {
    private Long id;
    @NotBlank(message = "Username cannot be blank!")
    @Size(min = 4, max = 14, message = "Username must be between 4 and 14 characters!")
    private String username;
    @NotBlank
    private Long companyId;
    @NotBlank
    private Position position;
    private CompanyDto companyInfo;
}
