package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.dto.people.employee.CreateEmployeeRequest;
import br.com.librigate.dto.people.employee.UpdateEmployeeRequest;

import org.springframework.http.ResponseEntity;

public interface IEmployeeService{

    ResponseEntity<?> create(CreateEmployeeRequest request);
    ResponseEntity<?> update(UpdateEmployeeRequest request);
    ResponseEntity<?> findByPK(String id) throws EntityNotFoundException;
    ResponseEntity<?> findAll();
    ResponseEntity<?> delete(String id);
}
