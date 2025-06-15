package aidyn.kelbetov.entity;

import aidyn.kelbetov.dto.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username cannot be blank!")
    @Size(min = 4, max = 14, message = "Username must be between 4 and 14 characters!")
    private String username;
    @JsonIgnore
    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 6, message = "Password must be at least 6 characters!")
    private String password;
    private Long companyId;
    @Enumerated(EnumType.STRING)
    private Position position;
}
