package br.com.librigate.model.service.people.factory;

import br.com.librigate.dto.people.customer.CreateCustomerRequest;
import br.com.librigate.dto.people.customer.UpdateCustomerRequest;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.mapper.people.CustomerMapper;
import br.com.librigate.model.service.address.AddressService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomerFactory {

    private final AddressService addressService;

    public CustomerFactory() {
        addressService = new AddressService();
    }

    public Customer createCustomer(CreateCustomerRequest request) {
        var address = addressService.create(request.address());
        Customer customer = CustomerMapper.instance.toEntity(request);
        customer.setAddress(address);
        customer.setPurchases(new ArrayList<>());
        customer.setRentList(new ArrayList<>());

        return customer;
    }

    public Customer updateCustomer(UpdateCustomerRequest request, Customer customer) {
        request.firstName().ifPresent(customer::setFirstName);
        request.lastName().ifPresent(customer::setLastName);
        request.telephone().ifPresent(customer::setTelephone);

        request.address().ifPresent((address) -> {
            var updatedAddress = addressService
                    .update(customer.getAddress().getId(), address);
            customer.setAddress(updatedAddress);
        });

        return customer;
    }
}
