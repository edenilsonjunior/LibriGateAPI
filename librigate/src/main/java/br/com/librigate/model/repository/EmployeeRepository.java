package br.com.librigate.model.repository;

import br.com.librigate.model.entity.people.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
