package br.com.librigate.controller;

import br.com.librigate.model.dto.employee.CreateEmployeeRequest;
import br.com.librigate.model.dto.employee.UpdateEmployeeRequest;
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
    public Employee createEmployee(@RequestBody CreateEmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping("/update")
    public Employee updateEmployee(@RequestBody UpdateEmployeeRequest request) {
        return employeeService.update(request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.delete(id);
    }

    @GetMapping("/find/{id}")
    public Employee findEmployee(@PathVariable String id) {
        return employeeService.findByPK(id);
    }

    @GetMapping("/all")
    public List<Employee> findAllEmployees() {
        return employeeService.findAll();
    }
}
