package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.customer.buy.BuyRequest;
import org.springframework.http.ResponseEntity;

public interface IBuyService {

    ResponseEntity<?> findByPK(Long id);

    ResponseEntity<?> purchase(BuyRequest request);
    ResponseEntity<?> processPayment(Long buyId);
    ResponseEntity<?> getPurchases(String cpf);
    ResponseEntity<?> getPurchaseById(String cpf, Long paymentId);
}
