package aidyn.kelbetov.mapper;

import aidyn.kelbetov.dto.CustomerDto;
import aidyn.kelbetov.dto.RegisterDto;
import aidyn.kelbetov.entity.Customer;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomerMapper {
    public static CustomerDto toDto(Customer entity){
        CustomerDto dto = new CustomerDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPosition(entity.getPosition());
        dto.setCompanyId(entity.getCompanyId());
        return dto;
    }

    public static Customer toEntity(CustomerDto dto){
        Customer customer = new Customer();
        customer.setUsername(dto.getUsername());
        customer.setPosition(dto.getPosition());
        customer.setCompanyId(dto.getCompanyId());
        return customer;
    }

    public static Customer fromRegisterDto(RegisterDto dto, PasswordEncoder encoder) {
        Customer customer = new Customer();
        customer.setUsername(dto.getUsername());
        customer.setPassword(encoder.encode(dto.getPassword()));
        return customer;
    }

}
