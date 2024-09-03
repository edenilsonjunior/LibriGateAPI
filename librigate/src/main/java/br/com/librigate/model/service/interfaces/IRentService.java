package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.actions.rent.RentRequest;
import org.springframework.http.ResponseEntity;

public interface IRentService {

    ResponseEntity<?> rent(RentRequest request);
    ResponseEntity<?> processDevolutionBook(Long rentId);
    ResponseEntity<?> renewRent(Long rentId);
    ResponseEntity<?> getRents(String cpf);
    ResponseEntity<?> getRendById(Long rentId);
}
