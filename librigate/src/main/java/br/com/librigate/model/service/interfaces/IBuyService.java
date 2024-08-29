package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.actions.buy.BuyRequest;
import org.springframework.http.ResponseEntity;

public interface IBuyService {

    ResponseEntity<?> purchase(BuyRequest request);
    ResponseEntity<?> processPayment(Long buyId);
    ResponseEntity<?> getPurchases(String cpf);
    ResponseEntity<?> getPurchaseById(String cpf, Long paymentId);
    ResponseEntity<?> cancelPurchase(Long buyId);

    ResponseEntity<?> findByPK(Long id);
}
