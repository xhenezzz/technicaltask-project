package aidyn.kelbetov.repo;

import aidyn.kelbetov.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public boolean existsByUsername(String username);
    Page<Customer> findByCompanyId(Long companyId, Pageable pageable);
}
