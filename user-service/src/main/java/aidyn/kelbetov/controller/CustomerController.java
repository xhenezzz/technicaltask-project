package aidyn.kelbetov.controller;

import aidyn.kelbetov.dto.CustomPage;
import aidyn.kelbetov.dto.CustomerDto;
import aidyn.kelbetov.dto.Position;
import aidyn.kelbetov.dto.RegisterDto;
import aidyn.kelbetov.entity.Customer;
import aidyn.kelbetov.repo.CustomerRepository;
import aidyn.kelbetov.service.CustomerService;
import feign.Request;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;
    private final CustomerRepository repository;

    public CustomerController(CustomerService service, CustomerRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/info/{customerId}")
    public CustomerDto getInfoAboutCustomer(@PathVariable Long customerId){
        return service.getInfoAboutCustomer(customerId);
    }

    @GetMapping("/info/all")
    public Page<CustomerDto> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue =  "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return service.getAllCustomers(pageable);
    }

    @GetMapping("/info/{customerId}/with-company")
    public CustomerDto getCustomerWithCompany(@PathVariable Long customerId){
        return service.getCustomerWithCompanyInfo(customerId);
    }


    @PostMapping("/create")
    public CustomerDto createCustomer(@Valid @RequestBody RegisterDto registerDto){
        return service.createCustomer(registerDto);
    }

    @DeleteMapping("/delete/{customerId}")
    public void deleteCustomerById(@PathVariable Long customerId){
        service.deleteCustomer(customerId);
    }

    @DeleteMapping("/{customerId}/company/")
    public CustomerDto removeFromCompany(@PathVariable Long customerId){
        return service.removeCustomerFromCompany(customerId);
    }

    @PutMapping("/update/{customerId}")
    public CustomerDto editCustomerInfo(@Valid @RequestBody CustomerDto customerDto,
                                        @PathVariable Long customerId){
        return service.editCustomer(customerId, customerDto);
    }

    @PutMapping("/{customerId}/company/{companyId}")
    public CustomerDto addToCompany(@PathVariable Long customerId, @PathVariable Long companyId, @RequestParam Position position){
        return service.addCustomerToCompany(customerId, companyId, position);
    }

    @GetMapping("/debug/all")
    public List<Customer> getAllCustomersDebug(){
        return repository.findAll();
    }

    @GetMapping("/customer/by-company/{companyId}")
    public CustomPage<CustomerDto> getByCompanyId(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getCustomersByCompanyId(companyId, page, size);
    }
}
