package br.com.librigate.model.service.people;

import br.com.librigate.dto.people.customer.CreateCustomerRequest;
import br.com.librigate.dto.people.customer.UpdateCustomerRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.mapper.people.CustomerMapper;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.address.AddressService;
import br.com.librigate.model.service.interfaces.ICustomerService;
import br.com.librigate.model.service.people.factory.CustomerFactory;
import br.com.librigate.model.service.people.validator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;
    private final CustomerFactory customerFactory;
    private final AddressService addressService;
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private final HandleRequest handleRequest;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerValidator customerValidator, 
    CustomerFactory customerFactory, AddressService addressService, HandleRequest handleRequest) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
        this.customerFactory = customerFactory;
        this.addressService = addressService;
        this.handleRequest = handleRequest;
    }


    @Override
    public ResponseEntity<?> findAll() {
        return handleRequest.handle(() -> {

            var entityList = customerRepository.findAll();
            var response = entityList.stream().map(customerMapper::toResponse).toList();

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> findByCPF(String cpf) {
        return handleRequest.handle(() -> {

            var entity = findByCustomerByCPF(cpf);
            var response = customerMapper.toResponse(entity);

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

     
    @Override
    public ResponseEntity<?> create(CreateCustomerRequest request) {
        return handleRequest.handle(() -> {

            customerValidator.validateNewCustomer(request);
            var address = addressService.create(request.address());
            var customer = customerFactory.createCustomer(request);

            customer.setAddress(address);
            customerRepository.save(customer);

            var response = customerMapper.toResponse(customer);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }

     
    @Override
    public ResponseEntity<?> update(UpdateCustomerRequest request) {
        return handleRequest.handle(() -> {
            var customer = customerFactory.updateCustomer(request, findByCustomerByCPF(request.cpf()));
            customerRepository.save(customer);

            var response = customerMapper.toResponse(customer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

     
    @Override
    public ResponseEntity<?> delete(String cpf) {
        return handleRequest.handle(() -> {
            var customer = findByCustomerByCPF(cpf);
            customer.setActive(false);

            customerRepository.save(customer);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        });
    }

    private Customer findByCustomerByCPF(String cpf) {
        return customerRepository.findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
}
