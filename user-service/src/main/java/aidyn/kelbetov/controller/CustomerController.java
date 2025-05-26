package aidyn.kelbetov.controller;

import aidyn.kelbetov.entity.Customer;
import aidyn.kelbetov.entity.CustomerDto;
import aidyn.kelbetov.entity.RegisterDto;
import aidyn.kelbetov.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/info/{customerId}")
    public ResponseEntity<Customer> getInfoAboutCustomer(@PathVariable Long customerId){
        Customer customer = service.getInfoAboutCustomer(customerId);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody RegisterDto registerDto){
        Customer customer = service.createCustomer(registerDto);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/delete/{customerId}")
    public void deleteCustomerById(@PathVariable Long customerId){
        service.deleteCustomer(customerId);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<Customer> editCustomerInfo(@RequestBody CustomerDto customerDto, @PathVariable Long customerId){
        Customer customer = service.editCustomer(customerId, customerDto);
        return ResponseEntity.ok(customer);
    }
}
