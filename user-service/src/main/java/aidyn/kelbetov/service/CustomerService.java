package aidyn.kelbetov.service;

import aidyn.kelbetov.entity.Customer;
import aidyn.kelbetov.dto.CustomerDto;
import aidyn.kelbetov.dto.RegisterDto;
import aidyn.kelbetov.mapper.CustomerMapper;
import aidyn.kelbetov.repo.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerDto createCustomer(RegisterDto registerDto){
        validateUsernameUnique(registerDto.getUsername());
        Customer customer = CustomerMapper.fromRegisterDto(registerDto, passwordEncoder);
        Customer saved = repository.save(customer);
        return CustomerMapper.toDto(saved);
    }

    public CustomerDto editCustomer(Long customerId, CustomerDto customerDto) {
        Customer existingCustomer = validateAndGetCustomer(customerId);
        updateCustomerFields(existingCustomer, customerDto);
        Customer savedCustomer = repository.save(existingCustomer);
        return CustomerMapper.toDto(savedCustomer);
    }


    public void deleteCustomer(Long customerId){
        Customer existingCustomer = validateAndGetCustomer(customerId);
        repository.delete(existingCustomer);
    }

    public CustomerDto getInfoAboutCustomer(Long customerId){
        Customer existingCustomer = validateAndGetCustomer(customerId);
        return CustomerMapper.toDto(existingCustomer);
    }

    public Page<CustomerDto> getAllCustomers(Pageable pageable){
        Page<Customer> customers = repository.findAll(pageable);

        return customers.map(CustomerMapper::toDto);
    }

    public void validateUsernameUnique(String username){
        if(repository.existsByUsername(username)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
        }
    }

    public Customer validateAndGetCustomer(Long customerId){
        return repository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }

    private void updateCustomerFields(Customer existing, CustomerDto dto) {
        if (dto.getUsername() != null) {
            existing.setUsername(dto.getUsername());
        }
        if (dto.getPosition() != null) {
            existing.setPosition(dto.getPosition());
        }
        if (dto.getCompanyId() != null) {
            existing.setCompanyId(dto.getCompanyId());
        }
    }

}
