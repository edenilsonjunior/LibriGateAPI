package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.actions.buy.BuyRequest;
import org.springframework.http.ResponseEntity;

public interface IBuyService {

    ResponseEntity<?> purchase(BuyRequest request);
    ResponseEntity<?> processPayment(Long buyId);
    ResponseEntity<?> cancelPurchase(Long buyId);

    ResponseEntity<?> getPurchasesByCustomerCpf(String cpf);
    ResponseEntity<?> findByPK(Long id);
}
