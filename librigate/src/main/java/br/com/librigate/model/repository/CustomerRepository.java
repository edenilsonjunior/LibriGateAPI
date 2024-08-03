package br.com.librigate.model.repository;

import br.com.librigate.model.entity.people.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
