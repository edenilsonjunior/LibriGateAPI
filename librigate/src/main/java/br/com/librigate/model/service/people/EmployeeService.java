package br.com.librigate.model.service.people;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.employee.CreateEmployeeRequest;
import br.com.librigate.model.dto.employee.UpdateEmployeeRequest;

import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.repository.EmployeeRepository;
import br.com.librigate.model.service.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public Employee create(CreateEmployeeRequest request) {

        // try{
        //     var address = addressService.create(request.address());


        //     Employee employee = EmployeeMapper.INSTANCE.toEntity(request);
        //     employee.setAddress(asyncAddressCreation.get());
        //     employee.setRestockList(new ArrayList<>());

        //     return employeeRepository.save(employee);

        // }catch(Exception ex){
        //     System.out.println("\n\n\n\n\n\n " + ex.getMessage() + "\n\n\n\n\n\n");
        //     throw new RuntimeException("Error creating employee");
        // }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Employee update(UpdateEmployeeRequest request) {

        // try{
        //     var employee = employeeRepository
        //             .findById(request.cpf())
        //             .orElseThrow();

        //     var addressCompletableFuture = CompletableFuture.supplyAsync(() -> addressService.create(request.address()));

        //     employee.setFirstName(request.firstName());
        //     employee.setLastName(request.lastName());
        //     employee.setBirthDate(request.birthDate());
        //     employee.setGender(request.gender());
        //     employee.setTelephone(request.telephone());
        //     employee.setRole(request.role());
        //     employee.setPassword(request.password());
        //     employee.setRestockList(new ArrayList<>());
        //     employee.setActive(true);

        //     var address = addressCompletableFuture.get();
        //     employee.setAddress(address);

        //     return employeeRepository.save(employee);

        // }catch(Exception ex){
        //     throw new RuntimeException("Error updating employee");
        // }
    
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Employee findByPK(String id) throws EntityNotFoundException {
        return employeeRepository
            .findById(id)
            .orElseThrow(()-> new EntityNotFoundException("Employee not found"));
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void delete(String id) {
        try{

            var employee = employeeRepository
                    .findById(id)
                    .orElseThrow();

            employee.setActive(false);

            employeeRepository.save(employee);

        }catch(Exception ex){
            throw new RuntimeException("Error deleting employee");
        }
    }
}
