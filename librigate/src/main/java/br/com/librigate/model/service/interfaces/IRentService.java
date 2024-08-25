package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.dto.actions.rent.RentResponse;
import br.com.librigate.model.entity.actions.Rent;

import java.util.List;

public interface IRentService {

    RentResponse rent(RentRequest request);
    RentResponse processDevolutionBook(Long rentId);
    List<Rent> getRents(String cpf);
    Rent getRendById(Long rentId);
}
