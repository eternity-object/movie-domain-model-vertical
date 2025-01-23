package org.eternity.domainmodel.movie.persistence;

import org.eternity.domainmodel.movie.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
