package aidyn.kelbetov.feign;

import aidyn.kelbetov.dto.CustomPage;
import aidyn.kelbetov.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface CustomerServiceClient {
    @GetMapping("/customer/by-company/{companyId}")
    CustomPage<CustomerDto> getCustomersByCompanyId(
            @PathVariable Long companyId,
            @RequestParam int page,
            @RequestParam int size
    );
}
