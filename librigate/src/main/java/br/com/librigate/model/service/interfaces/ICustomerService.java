package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.customer.CreateCustomerRequest;
import br.com.librigate.model.dto.customer.UpdateCustomerRequest;
import br.com.librigate.model.dto.customer.review.ReviewRequest;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.people.Customer;

import java.util.List;

public interface ICustomerService  {

    Customer create(CreateCustomerRequest request);
    Customer update(UpdateCustomerRequest request);
    Customer findByPK(String id) throws EntityNotFoundException;
    List<Customer> findAll();
    void delete(String id);

    Review reviewBook(ReviewRequest request);
}
