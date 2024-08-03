package br.com.librigate.model.service.people;

import br.com.librigate.model.dto.BuyDTO;
import br.com.librigate.model.dto.CustomerDTO;
import br.com.librigate.model.dto.RentDTO;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.service.interfaces.ICustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerService implements ICustomerService {

    @Override
    public Customer create(CustomerDTO dto) {
        return null;
    }

    @Override
    public Customer update(String id, CustomerDTO dto) {
        return null;
    }

    @Override
    public Optional<Customer> findByPK(String id) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Buy purchase(BuyDTO dto) {
        return null;
    }

    @Override
    public Buy processPayment(String cpf, Long paymentId) {
        return null;
    }

    @Override
    public List<Buy> getPurchases(String cpf) {
        return null;
    }

    @Override
    public Buy getPurchaseById(String cpf, Long paymentId) {
        return null;
    }

    @Override
    public Rent rent(String cpf, RentDTO rendtDTO) {
        return null;
    }

    @Override
    public Rent processDevolutionBook(Long rentId) {
        return null;
    }

    @Override
    public List<Rent> getRents(String cpf) {
        return null;
    }

    @Override
    public Rent getRendById(String cpf, Long rentId) {
        return null;
    }

    @Override
    public Rent getRentById(String cpf, Long rentId) {
        return null;
    }

    @Override
    public void reviewBook(Review review) {

    }
}
