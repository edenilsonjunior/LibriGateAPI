package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.actions.rent.RentRequest;
import org.springframework.http.ResponseEntity;

public interface IRentService {

    ResponseEntity<?> findRentsByCustomerCPF(String cpf);
    ResponseEntity<?> findRendById(Long rentId);
    ResponseEntity<?> rent(RentRequest request);
    ResponseEntity<?> renewRent(Long rentId);
    ResponseEntity<?> processDevolutionBook(Long rentId);
}
