package aidyn.kelbetov.service;

import aidyn.kelbetov.entity.Customer;
import aidyn.kelbetov.entity.CustomerDto;
import aidyn.kelbetov.entity.RegisterDto;
import aidyn.kelbetov.repo.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer createCustomer(RegisterDto registerDto){
        Customer customer = new Customer();
        customer.setUsername(registerDto.getUsername());
        customer.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        return repository.save(customer);
    }

    public Customer editCustomer(Long customerId, CustomerDto customerDto) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        if (customerDto.getUsername() != null) {
            customer.setUsername(customerDto.getUsername());
        }

        if (customerDto.getPosition() != null) {
            customer.setPosition(customerDto.getPosition());
        }

        if (customerDto.getCompanyId() != null) {
            customer.setCompanyId(customerDto.getCompanyId());
        }

        return repository.save(customer);
    }


    public void deleteCustomer(Long customerId){
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        repository.delete(customer);
    }

    public Customer getInfoAboutCustomer(Long customerId){
        return repository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }

}
