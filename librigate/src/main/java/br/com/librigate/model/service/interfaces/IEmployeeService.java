package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.people.employee.CreateEmployeeRequest;
import br.com.librigate.dto.people.employee.UpdateEmployeeRequest;
import org.springframework.http.ResponseEntity;

public interface IEmployeeService{

    ResponseEntity<?> findAll();
    ResponseEntity<?> findByPK(String id);
    ResponseEntity<?> create(CreateEmployeeRequest request);
    ResponseEntity<?> update(UpdateEmployeeRequest request);
    ResponseEntity<?> delete(String id);
}
