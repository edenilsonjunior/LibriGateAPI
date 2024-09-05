package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.actions.buy.BuyRequest;
import org.springframework.http.ResponseEntity;

public interface IBuyService {

    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> findPurchasesByCustomerCpf(String cpf);
    ResponseEntity<?> purchase(BuyRequest request);
    ResponseEntity<?> processPayment(Long buyId);
    ResponseEntity<?> cancelPurchase(Long buyId);
}
