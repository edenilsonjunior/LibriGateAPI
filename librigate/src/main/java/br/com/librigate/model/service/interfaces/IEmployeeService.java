package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.employee.EmployeeRequest;
import br.com.librigate.model.dto.employee.book.NewBookRequest;
import br.com.librigate.model.dto.employee.book.RestockBookRequest;
import br.com.librigate.model.dto.employee.book.RestockResponse;
import br.com.librigate.model.entity.people.Employee;

import java.util.List;

public interface IEmployeeService extends IService<Employee, EmployeeRequest, String> {

    RestockResponse buyNewBook(NewBookRequest request) throws Exception;
    RestockResponse restockBook(RestockBookRequest request) throws Exception;
    List<RestockResponse> getRestockHistory();
}

