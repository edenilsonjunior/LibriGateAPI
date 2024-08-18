package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.BuyDTO;
import br.com.librigate.model.dto.CustomerDTO;
import br.com.librigate.model.dto.RentDTO;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.people.Customer;

import java.util.List;

public interface ICustomerService extends IService<Customer, CustomerDTO, String> {

    Buy purchase(BuyDTO dto);
    Buy processPayment(String cpf, Long paymentId);
    List<Buy> getPurchases(String cpf);
    Buy getPurchaseById(String cpf, Long paymentId);

    Rent rent(String cpf, RentDTO rendtDTO);
    Rent processDevolutionBook(Long rentId);
    List<Rent> getRents(String cpf);
    Rent getRendById(String cpf, Long rentId);
    Rent getRentById(String cpf, Long rentId);

    Review reviewBook(Review review);
}
