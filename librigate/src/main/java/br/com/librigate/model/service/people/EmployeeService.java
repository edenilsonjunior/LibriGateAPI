package br.com.librigate.model.service.people;

import br.com.librigate.dto.people.employee.CreateEmployeeRequest;
import br.com.librigate.dto.people.employee.UpdateEmployeeRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.mapper.people.EmployeeMapper;
import br.com.librigate.model.repository.EmployeeRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.interfaces.IEmployeeService;
import br.com.librigate.model.service.people.factory.EmployeeFactory;
import br.com.librigate.model.service.people.validator.EmployeeValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeValidator employeeValidator;
    private final EmployeeFactory employeeFactory;
    private final EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;
    private final HandleRequest handleRequest;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeValidator employeeValidator,
            EmployeeFactory employeeFactory, HandleRequest handleRequest) {
        this.employeeRepository = employeeRepository;
        this.employeeValidator = employeeValidator;
        this.employeeFactory = employeeFactory;
        this.handleRequest = handleRequest;
    }

    @Override
    public ResponseEntity<?> findAll() {
        return handleRequest.handle(() -> {

            var entityList = employeeRepository.findAll();

            var response = entityList.stream()
                    .map(employeeMapper::toResponse)
                    .toList();

            if (response.isEmpty())
                return new ResponseEntity<>(new ArrayList<Employee>(), HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> findByCPF(String cpf) {
        return handleRequest.handle(() -> {

            var entity = employeeRepository.findById(cpf)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

            var response = employeeMapper.toResponse(entity);

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

     
    @Override
    public ResponseEntity<?> create(CreateEmployeeRequest request) {
        return handleRequest.handle(() -> {

            employeeValidator.validateNewEmployee(request);
            var employee = employeeFactory.createEmployee(request);
            employeeRepository.save(employee);

            var response = employeeMapper.toResponse(employee);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }

     
    @Override
    public ResponseEntity<?> update(UpdateEmployeeRequest request) {
        return handleRequest.handle(() -> {

            var employee = employeeFactory.updateEmployee(request, findEmployeeByCPF(request.cpf()));
            employeeRepository.save(employee);

            var response = employeeMapper.toResponse(employee);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

     
    @Override
    public ResponseEntity<?> delete(String cpf) {

        return handleRequest.handle(() -> {
            var employee = findEmployeeByCPF(cpf);
            employee.setActive(false);

            employeeRepository.save(employee);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        });
    }

    private Employee findEmployeeByCPF(String cpf) {

        return employeeRepository
                .findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }
}
