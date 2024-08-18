package br.com.librigate.controller;

import br.com.librigate.model.dto.employee.EmployeeRequest;
import br.com.librigate.model.dto.employee.book.NewBookRequest;
import br.com.librigate.model.dto.employee.book.RestockBookRequest;
import br.com.librigate.model.dto.employee.book.RestockResponse;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.service.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;




    @PostMapping("/create")
    public Employee createEmployee(EmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping("/update")
    public Employee updateEmployee(EmployeeRequest request) {
        return employeeService.update(request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.delete(id);
    }

    @GetMapping("/find/{id}")
    public Employee findEmployee(@PathVariable String id) {
        return employeeService.findByPK(id).orElse(null);
    }

    @GetMapping("/all")
    public List<Employee> findAllEmployees() {
        return employeeService.findAll();
    }

    @PostMapping("/restock-new-book")
    public RestockResponse restockNewBook(NewBookRequest request) throws Exception {
        return employeeService.buyNewBook(request);
    }

    @PostMapping("/restock-book")
    public RestockResponse restockBook(RestockBookRequest request) throws Exception {
        return employeeService.restockBook(request);
    }

    @GetMapping("/restock-history")
    public List<RestockResponse> getRestockHistory() {
        return employeeService.getRestockHistory();
    }

}
