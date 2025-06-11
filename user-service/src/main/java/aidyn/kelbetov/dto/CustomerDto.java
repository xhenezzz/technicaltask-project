package aidyn.kelbetov.dto;

import aidyn.kelbetov.entity.Position;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;
    @NotBlank(message = "Username cannot be blank!")
    @Size(min = 4, max = 14, message = "Username must be between 4 and 14 characters!")
    public String username;
    public Long companyId;
    public Position position;
}
