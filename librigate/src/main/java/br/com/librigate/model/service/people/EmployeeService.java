package br.com.librigate.model.service.people;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.employee.CreateEmployeeRequest;
import br.com.librigate.model.dto.employee.UpdateEmployeeRequest;

import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.mapper.people.EmployeeMapper;
import br.com.librigate.model.repository.EmployeeRepository;
import br.com.librigate.model.service.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.*;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Transactional
    @Override
    public ResponseEntity<?> create(CreateEmployeeRequest request) {

        try {
            var address = addressService.create(request.address());

            Employee employee = EmployeeMapper.INSTANCE.toEntity(request);
            employee.setAddress(address);
            employee.setRestockList(new ArrayList<>());
            employee.setActive(true);

            var response = employeeRepository.save(employee);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (InvalidParameterException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> update(UpdateEmployeeRequest request) {

        try {
            var employee = employeeRepository
                    .findById(request.cpf())
                    .orElseThrow(()-> new EntityNotFoundException("Employee not found"));

            request.firstName().ifPresent(employee::setFirstName);
            request.lastName().ifPresent(employee::setLastName);
            request.telephone().ifPresent(employee::setTelephone);
            request.password().ifPresent(employee::setPassword);
            request.role().ifPresent(employee::setRole);

            request.address().ifPresent((address) -> {
                var updatedAddress = addressService
                        .update(employee.getAddress().getId(), address);
                employee.setAddress(updatedAddress);
            });

            var response =  employeeRepository.save(employee);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (InvalidParameterException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findByPK(String id) throws EntityNotFoundException {
        var response =  employeeRepository.findById(id);

        if(response.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAll() {
        var response = employeeRepository.findAll();

        if(response.isEmpty())
            return new ResponseEntity<>(new ArrayList<Employee>(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> delete(String id) {
        try {

            var employee = employeeRepository
                    .findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Employee not found"));

            employee.setActive(false);

            employeeRepository.save(employee);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
