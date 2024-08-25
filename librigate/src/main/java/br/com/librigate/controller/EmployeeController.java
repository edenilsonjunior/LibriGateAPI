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

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody CreateEmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@RequestBody UpdateEmployeeRequest request) {
        return employeeService.update(request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
       return employeeService.delete(id);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findEmployee(@PathVariable String id) {
        return employeeService.findByPK(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllEmployees() {
        return employeeService.findAll();
    }
}
