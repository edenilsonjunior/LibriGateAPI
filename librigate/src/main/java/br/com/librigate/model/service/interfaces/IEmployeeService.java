package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.people.employee.CreateEmployeeRequest;
import br.com.librigate.dto.people.employee.UpdateEmployeeRequest;
import org.springframework.http.ResponseEntity;

public interface IEmployeeService{

    ResponseEntity<?> findAll();
    ResponseEntity<?> findByCPF(String cpf);
    ResponseEntity<?> create(CreateEmployeeRequest request);
    ResponseEntity<?> update(UpdateEmployeeRequest request);
    ResponseEntity<?> delete(String id);
}
