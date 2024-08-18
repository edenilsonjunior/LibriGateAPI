package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.customer.CustomerRequest;
import br.com.librigate.model.dto.customer.buy.BuyRequest;
import br.com.librigate.model.dto.customer.buy.BuyResponse;
import br.com.librigate.model.dto.customer.rent.RentRequest;
import br.com.librigate.model.dto.customer.rent.RentResponse;
import br.com.librigate.model.dto.customer.review.ReviewRequest;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.people.Customer;

import java.util.List;

public interface ICustomerService extends IService<Customer, CustomerRequest, String> {

    BuyResponse purchase(BuyRequest request);
    BuyResponse processPayment(Long buyId);

    List<Buy> getPurchases(String cpf);
    Buy getPurchaseById(String cpf, Long paymentId);

    RentResponse rent(RentRequest request);
    RentResponse processDevolutionBook(Long rentId);

    List<Rent> getRents(String cpf);
    Rent getRendById(Long rentId);

    Review reviewBook(ReviewRequest request);
}
