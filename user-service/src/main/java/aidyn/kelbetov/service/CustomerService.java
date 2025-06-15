package aidyn.kelbetov.service;

import aidyn.kelbetov.dto.*;
import aidyn.kelbetov.entity.Customer;
import aidyn.kelbetov.feign.CompanyServiceClient;
import aidyn.kelbetov.mapper.CustomerMapper;
import aidyn.kelbetov.repo.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyServiceClient companyServiceClient;

    public CustomerService(CustomerRepository repository, PasswordEncoder passwordEncoder, CompanyServiceClient companyServiceClient) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.companyServiceClient = companyServiceClient;
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

    public CustomerDto getCustomerWithCompanyInfo(Long customerId){
        Customer customer = validateAndGetCustomer(customerId);
        CustomerDto customerDto = CustomerMapper.toDto(customer);
        if(customer.getCompanyId() != null){
            try {
                CompanyDto companyDto = companyServiceClient.getCompanyById(customer.getCompanyId());
                customerDto.setCompanyInfo(companyDto);
            } catch (Exception e){
                System.err.println("Failed to fetch company info: " + e.getMessage());
            }
        }

        return customerDto;
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

    public CustomerDto addCustomerToCompany(Long customerId, Long companyId, Position position){
        logger.info("Add customer {} to company {}", customerId, companyId);

        Customer customer = validateAndGetCustomer(customerId);

        try {
            companyServiceClient.getCompanyById(companyId);
        } catch (Exception e){
            logger.error("Company {} not found!", companyId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not found!");
        }

        customer.setCompanyId(companyId);
        customer.setPosition(position);
        Customer saved = repository.save(customer);

        logger.info("Customer {} added to company {} successfully!", customerId, companyId);
        return CustomerMapper.toDto(saved);
    }

    public CustomerDto removeCustomerFromCompany(Long customerId){
        logger.info("Remove customer {} from company", customerId);

        Customer customer = validateAndGetCustomer(customerId);
        if (customer.getCompanyId() == null) {
            logger.warn("Customer {} is not assigned to any company", customerId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer is not assigned to any company");
        }

        Long oldCompanyId = customer.getCompanyId();
        customer.setPosition(null);
        customer.setCompanyId(null);
        Customer saved = repository.save(customer);

        logger.info("Customer {} removed from company {} successfully!", customerId, oldCompanyId);
        return CustomerMapper.toDto(saved);
    }

    public CustomPage<CustomerDto> getCustomersByCompanyId(Long companyId, int page, int size) {
        Page<Customer> resultPage = repository.findByCompanyId(companyId, PageRequest.of(page, size));
        List<CustomerDto> content = resultPage.getContent().stream()
                .map(CustomerMapper::toDto)
                .toList();

        return new CustomPage<>(
                content,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements(),
                resultPage.getTotalPages(),
                resultPage.isLast()
        );
    }


}
