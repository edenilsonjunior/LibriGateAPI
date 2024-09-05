package br.com.librigate.model.service.people.factory;

import br.com.librigate.dto.people.customer.CreateCustomerRequest;
import br.com.librigate.dto.people.customer.UpdateCustomerRequest;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.mapper.people.CustomerMapper;
import br.com.librigate.model.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class CustomerFactory {

    private final IAddressService addressService;

    @Autowired
    public CustomerFactory(IAddressService addressService) {
        this.addressService = addressService;
    }

    public Customer createCustomer(CreateCustomerRequest request) {
        Customer customer = CustomerMapper.INSTANCE.toEntity(request);
        customer.setActive(true);
        customer.setPurchases(new ArrayList<>());
        customer.setRentList(new ArrayList<>());
        customer.setRegistrationDate(LocalDate.now());

        return customer;
    }

    public Customer updateCustomer(UpdateCustomerRequest request, Customer customer) {

        request.address().ifPresent((address) -> {
            var updatedAddress = addressService
                    .update(customer.getAddress().getId(), address);
            customer.setAddress(updatedAddress);
        });

        request.firstName().ifPresent(customer::setFirstName);
        request.lastName().ifPresent(customer::setLastName);
        request.telephone().ifPresent(customer::setTelephone);

        return customer;
    }
}
