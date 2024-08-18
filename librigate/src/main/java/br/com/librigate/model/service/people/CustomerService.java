package br.com.librigate.model.service.people;

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
import br.com.librigate.model.service.interfaces.ICustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerService implements ICustomerService {

    @Override
    public BuyResponse purchase(BuyRequest request) {
        return null;
    }

    @Override
    public BuyResponse processPayment(Long buyId) {
        return null;
    }

    @Override
    public List<Buy> getPurchases(String cpf) {
        return List.of();
    }

    @Override
    public Buy getPurchaseById(String cpf, Long paymentId) {
        return null;
    }

    @Override
    public RentResponse rent(RentRequest request) {
        return null;
    }

    @Override
    public RentResponse processDevolutionBook(Long rentId) {
        return null;
    }

    @Override
    public List<Rent> getRents(String cpf) {
        return List.of();
    }

    @Override
    public Rent getRendById(Long rentId) {
        return null;
    }

    @Override
    public Review reviewBook(ReviewRequest request) {
        return null;
    }

    @Override
    public Customer create(CustomerRequest customerRequest) {
        return null;
    }

    @Override
    public Customer update(CustomerRequest customerRequest) {
        return null;
    }

    @Override
    public Optional<Customer> findByPK(String id) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        return List.of();
    }

    @Override
    public void delete(String id) {

    }
}
