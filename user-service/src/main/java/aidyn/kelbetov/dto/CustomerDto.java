package aidyn.kelbetov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;
    @NotNull(message = "Username cannot be blank!")
    @Size(min = 4, max = 14, message = "Username must be between 4 and 14 characters!")
    private String username;
    @NotNull
    private Long companyId;
    @NotNull
    private Position position;
    private CompanyDto companyInfo;
}
