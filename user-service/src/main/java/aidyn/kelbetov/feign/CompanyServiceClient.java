package aidyn.kelbetov.feign;

import aidyn.kelbetov.dto.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service")
public interface CompanyServiceClient {
    @GetMapping("/company/info/{companyId}")
    CompanyDto getCompanyById(@PathVariable Long companyId);
}
