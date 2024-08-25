package br.com.librigate.model.service.actions;

import java.util.List;

import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.dto.actions.buy.BuyResponse;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.service.interfaces.IBuyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuyService implements IBuyService{

    @Override
    public Buy findByPK(Long id) {
        return null;
    }

    @Transactional
    @Override
    public BuyResponse purchase(BuyRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'purchase'");
    }

    @Transactional
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
