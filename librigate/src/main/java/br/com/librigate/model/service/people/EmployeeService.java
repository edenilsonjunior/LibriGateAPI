package br.com.librigate.model.service.people;

import br.com.librigate.model.dto.employee.EmployeeRequest;
import br.com.librigate.model.dto.employee.book.NewBookRequest;
import br.com.librigate.model.dto.employee.book.RestockBookRequest;
import br.com.librigate.model.dto.employee.book.RestockResponse;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.service.interfaces.IEmployeeService;

import java.util.List;
import java.util.Optional;

public class EmployeeService implements IEmployeeService {


    @Override
    public RestockResponse buyNewBook(NewBookRequest request) throws Exception {
        return null;
    }

    @Override
    public RestockResponse restockBook(RestockBookRequest request) throws Exception {
        return null;
    }

    @Override
    public List<RestockResponse> getRestockHistory() {
        return List.of();
    }

    @Override
    public Employee create(EmployeeRequest employeeRequest) {
        return null;
    }

    @Override
    public Employee update(EmployeeRequest employeeRequest) {
        return null;
    }

    @Override
    public Optional<Employee> findByPK(String id) {
        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }
}
