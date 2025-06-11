package aidyn.kelbetov.controller;

import aidyn.kelbetov.dto.CustomerDto;
import aidyn.kelbetov.dto.RegisterDto;
import aidyn.kelbetov.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
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

    @PostMapping("/create")
    public CustomerDto createCustomer(@Valid @RequestBody RegisterDto registerDto){
        return service.createCustomer(registerDto);
    }

    @DeleteMapping("/delete/{customerId}")
    public void deleteCustomerById(@PathVariable Long customerId){
        service.deleteCustomer(customerId);
    }

    @PutMapping("/update/{customerId}")
    public CustomerDto editCustomerInfo(@Valid @RequestBody CustomerDto customerDto,
                                        @PathVariable Long customerId){
        return service.editCustomer(customerId, customerDto);
    }
}
