package br.com.librigate.model.service.people;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.dto.people.customer.CreateCustomerRequest;
import br.com.librigate.dto.people.customer.UpdateCustomerRequest;
import br.com.librigate.model.entity.people.Person;
import br.com.librigate.model.mapper.people.CustomerMapper;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.service.address.AddressService;
import br.com.librigate.model.service.interfaces.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final AddressService addressService;

    public CustomerService(CustomerRepository customerRepository, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
    }

    @Transactional
    @Override
    public ResponseEntity<?> create(CreateCustomerRequest request) {
        try{
            var customer = CustomerMapper.instance.toEntity(request);
            var address = addressService.create(request.address());
            customer.setRegistrationDate(LocalDate.now());
            customer.setAddress(address);

            customer.setPurchases(new ArrayList<>());
            customer.setRentList(new ArrayList<>());
            customer.setActive(true);

            return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);

        } catch (InvalidParameterException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    @Override
    public ResponseEntity<?> update(UpdateCustomerRequest request) {
        try{
             var entity = customerRepository
                     .findById(request.cpf())
                     .orElseThrow(()-> new EntityNotFoundException("Customer not found"));

             request.address().ifPresent((address) -> {
                var updatedAddress = addressService
                        .update(entity.getAddress().getId(), address);
                        entity.setAddress(updatedAddress);
             });

             request.firstName().ifPresent(entity::setFirstName);
             request.lastName().ifPresent(entity::setLastName);
             request.telephone().ifPresent(entity::setTelephone);
             request.password().ifPresent(entity::setPassword);
             return new ResponseEntity<>(customerRepository.save(entity), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> findByPK(String id) throws EntityNotFoundException {
        var entity = customerRepository.findById(id);
        if(entity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(customerRepository.findAll()
                .stream().filter(Person::isActive), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> delete(String id) {
        try{
            var entity = customerRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Customer not found"));
            entity.setActive(false);
            customerRepository.save(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
