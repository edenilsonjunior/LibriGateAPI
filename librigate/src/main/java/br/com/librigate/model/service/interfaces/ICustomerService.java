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

    public Buy purchase(BuyDTO dto);
    public Buy processPayment(String cpf, Long paymentId);
    public List<Buy> getPurchases(String cpf);
    public Buy getPurchaseById(String cpf, Long paymentId);

    public Rent rent(String cpf, RentDTO rendtDTO);
    public Rent processDevolutionBook(Long rentId);
    public List<Rent> getRents(String cpf);
    public Rent getRendById(String cpf, Long rentId);
    public Rent getRentById(String cpf, Long rentId);

    public void reviewBook(Review review);
}
