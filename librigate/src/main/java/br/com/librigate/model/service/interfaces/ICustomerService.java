package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.dto.people.customer.CreateCustomerRequest;
import br.com.librigate.dto.people.customer.UpdateCustomerRequest;
import org.springframework.http.ResponseEntity;

public interface ICustomerService  {

    ResponseEntity<?> create(CreateCustomerRequest request);
    ResponseEntity<?> update(UpdateCustomerRequest request);
    ResponseEntity<?> findByPK(String id) throws EntityNotFoundException;
    ResponseEntity<?> findAll();
    ResponseEntity<?> delete(String id);
}
