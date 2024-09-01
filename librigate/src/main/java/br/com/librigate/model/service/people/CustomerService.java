package br.com.librigate.model.service.people;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.dto.people.customer.CreateCustomerRequest;
import br.com.librigate.dto.people.customer.UpdateCustomerRequest;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.interfaces.ICustomerService;
import br.com.librigate.model.service.people.factory.CustomerFactory;
import br.com.librigate.model.service.people.validator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;
    private final CustomerFactory customerFactory;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerValidator customerValidator, CustomerFactory customerFactory) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
        this.customerFactory = customerFactory;
    }

    @Transactional
    @Override
    public ResponseEntity<?> create(CreateCustomerRequest request) {
        return HandleRequest.handle(() -> {
            customerValidator.validateNewCustomer(request);

            var customer = customerFactory.createCustomer(request);
            var response = customerRepository.save(customer);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }


    @Transactional
    @Override
    public ResponseEntity<?> update(UpdateCustomerRequest request) {
        return HandleRequest.handle(()->{
            var customer = customerFactory.updateCustomer(request, findByCustomerByCPF(request.cpf()));

            var response = customerRepository.save(customer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> findByPK(String id){
        return HandleRequest.handle(()->{
            var response = customerRepository.findById(id);

            if(response.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> findAll() {
        return HandleRequest.handle(()->{
            var response = customerRepository.findAll();

            if(response.isEmpty()){
                return new ResponseEntity<>(new ArrayList<Customer>(), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Transactional
    @Override
    public ResponseEntity<?> delete(String cpf) {
        return HandleRequest.handle(()->{
            var customer = findByCustomerByCPF(cpf);
            customer.setActive(false);

            customerRepository.save(customer);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        });
    }

    private Customer findByCustomerByCPF(String cpf) throws EntityNotFoundException {
        return customerRepository.findById(cpf)
                .orElseThrow(()-> new EntityNotFoundException("Customer not found"));
    }
}
