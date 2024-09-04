package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.people.customer.CreateCustomerRequest;
import br.com.librigate.dto.people.customer.UpdateCustomerRequest;
import org.springframework.http.ResponseEntity;

public interface ICustomerService  {

    ResponseEntity<?> findAll();
    ResponseEntity<?> findByCPF(String cpf);
    ResponseEntity<?> create(CreateCustomerRequest request);
    ResponseEntity<?> update(UpdateCustomerRequest request);
    ResponseEntity<?> delete(String id);
}
