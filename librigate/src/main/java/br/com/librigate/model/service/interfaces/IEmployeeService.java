package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.employee.CreateEmployeeRequest;
import br.com.librigate.model.dto.employee.UpdateEmployeeRequest;
import br.com.librigate.model.entity.people.Employee;

import java.util.List;

public interface IEmployeeService{

    Employee create(CreateEmployeeRequest request);
    Employee update(UpdateEmployeeRequest request);
    Employee findByPK(String id) throws EntityNotFoundException;
    List<Employee> findAll();
    void delete(String id);
}
