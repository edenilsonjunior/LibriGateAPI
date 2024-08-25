package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.dto.actions.buy.BuyResponse;
import br.com.librigate.model.entity.actions.Buy;

import java.util.List;

public interface IBuyService {

    Buy findByPK(Long id);
    BuyResponse purchase(BuyRequest request);
    BuyResponse processPayment(Long buyId);
    List<Buy> getPurchases(String cpf);
    Buy getPurchaseById(String cpf, Long paymentId);
}
