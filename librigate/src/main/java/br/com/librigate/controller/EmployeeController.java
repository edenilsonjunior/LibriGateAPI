package br.com.librigate.controller;

import br.com.librigate.dto.people.employee.CreateEmployeeRequest;
import br.com.librigate.dto.people.employee.UpdateEmployeeRequest;
import br.com.librigate.model.service.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> findAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> findEmployee(@PathVariable String cpf) {
        return employeeService.findByCPF(cpf);
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody CreateEmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody UpdateEmployeeRequest request) {
        return employeeService.update(request);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String cpf) {
       return employeeService.delete(cpf);
    }

}
