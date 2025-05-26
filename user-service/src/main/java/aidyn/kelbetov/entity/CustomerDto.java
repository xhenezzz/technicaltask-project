package aidyn.kelbetov.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    public String username;
    public Long companyId;
    @Enumerated(EnumType.STRING)
    public Position position;
}
