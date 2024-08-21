package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.employee.CreateEmployeeRequest;
import br.com.librigate.model.dto.employee.UpdateEmployeeRequest;
import br.com.librigate.model.entity.people.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEmployeeService{

    ResponseEntity<?> create(CreateEmployeeRequest request);
    ResponseEntity<?> update(UpdateEmployeeRequest request);
    ResponseEntity<?> findByPK(String id) throws EntityNotFoundException;
    ResponseEntity<?> findAll();
    ResponseEntity<?> delete(String id);
}
