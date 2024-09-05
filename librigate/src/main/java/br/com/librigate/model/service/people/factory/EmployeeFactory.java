package br.com.librigate.model.service.people.factory;

import br.com.librigate.dto.people.employee.CreateEmployeeRequest;
import br.com.librigate.dto.people.employee.UpdateEmployeeRequest;
import br.com.librigate.model.entity.address.Address;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.mapper.people.EmployeeMapper;
import br.com.librigate.model.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EmployeeFactory {

    private final AddressService addressService;

    @Autowired
    public EmployeeFactory(AddressService addressService) {
        this.addressService = addressService;
    }

    public Employee createEmployee(CreateEmployeeRequest request) {

        Address address = null;
        address = addressService.create(request.address());

        Employee employee = EmployeeMapper.INSTANCE.toEntity(request);
        employee.setAddress(address);
        employee.setRestockList(new ArrayList<>());
        employee.setActive(true);

        return employee;
    }

    public Employee updateEmployee(UpdateEmployeeRequest request, Employee employee) {

        request.firstName().ifPresent(employee::setFirstName);
        request.lastName().ifPresent(employee::setLastName);
        request.telephone().ifPresent(employee::setTelephone);
        request.role().ifPresent(employee::setRole);

        request.address().ifPresent((address) -> {
            var updatedAddress = addressService
                    .update(employee.getAddress().getId(), address);
            employee.setAddress(updatedAddress);
        });

        return employee;
    }
}
