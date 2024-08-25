package br.com.librigate.model.service.actions;

import java.util.List;

import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.dto.actions.rent.RentResponse;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.service.interfaces.IRentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RentService implements IRentService{

    @Transactional
    @Override
    public RentResponse rent(RentRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rent'");
    }

    @Transactional
    @Override
    public RentResponse processDevolutionBook(Long rentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processDevolutionBook'");
    }

    @Override
    public List<Rent> getRents(String cpf) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRents'");
    }

    @Override
    public Rent getRendById(Long rentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRendById'");
    }

}
