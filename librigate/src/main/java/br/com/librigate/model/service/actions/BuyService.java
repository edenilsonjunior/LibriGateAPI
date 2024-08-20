package br.com.librigate.model.service.actions;

import java.util.List;

import br.com.librigate.model.dto.customer.buy.BuyRequest;
import br.com.librigate.model.dto.customer.buy.BuyResponse;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.service.interfaces.IBuyService;
import org.springframework.stereotype.Service;

@Service
public class BuyService implements IBuyService{

    @Override
    public Buy findByPK(Long id) {
        return null;
    }

    @Override
    public BuyResponse purchase(BuyRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'purchase'");
    }

    @Override
    public BuyResponse processPayment(Long buyId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processPayment'");
    }

    @Override
    public List<Buy> getPurchases(String cpf) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPurchases'");
    }

    @Override
    public Buy getPurchaseById(String cpf, Long paymentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPurchaseById'");
    }
}
