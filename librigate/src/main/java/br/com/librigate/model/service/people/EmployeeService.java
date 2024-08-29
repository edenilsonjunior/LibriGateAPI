package br.com.librigate.model.service.people;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.dto.people.employee.CreateEmployeeRequest;
import br.com.librigate.dto.people.employee.UpdateEmployeeRequest;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.repository.EmployeeRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.interfaces.IEmployeeService;
import br.com.librigate.model.service.people.factory.EmployeeFactory;
import br.com.librigate.model.service.people.validator.EmployeeValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeValidator employeeValidator;
    private final EmployeeFactory employeeFactory;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeValidator employeeValidator, EmployeeFactory employeeFactory) {
        this.employeeRepository = employeeRepository;
        this.employeeValidator = employeeValidator;
        this.employeeFactory = employeeFactory;
    }


    @Transactional
    @Override
    public ResponseEntity<?> create(CreateEmployeeRequest request) {

        return HandleRequest.handle(() -> {
            employeeValidator.validateNewEmployee(request);

            var employee = employeeFactory.createEmployee(request);
            var response = employeeRepository.save(employee);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }

    @Transactional
    @Override
    public ResponseEntity<?> update(UpdateEmployeeRequest request) {

        return HandleRequest.handle(() -> {
            var employee = employeeFactory.updateEmployee(request, findEmployeeByCPF(request.cpf()));

            var response = employeeRepository.save(employee);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> findByPK(String id) {

        return HandleRequest.handle(() -> {
            var response = employeeRepository.findById(id);

            if (response.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> findAll() {

        return HandleRequest.handle(() -> {
            var response = employeeRepository.findAll();

            if (response.isEmpty())
                return new ResponseEntity<>(new ArrayList<Employee>(), HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Transactional
    @Override
    public ResponseEntity<?> delete(String cpf) {

        return HandleRequest.handle(() -> {
            var employee = findEmployeeByCPF(cpf);
            employee.setActive(false);

            employeeRepository.save(employee);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        });
    }

    private Employee findEmployeeByCPF(String cpf) throws EntityNotFoundException {

        return employeeRepository
                .findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }
}
