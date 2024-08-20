package br.com.librigate.model.service.people;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.customer.CreateCustomerRequest;
import br.com.librigate.model.dto.customer.UpdateCustomerRequest;
import br.com.librigate.model.dto.customer.review.ReviewRequest;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.mapper.interfaces.CustomerMapper;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.service.book.BookService;
import br.com.librigate.model.service.interfaces.ICustomerService;

import java.util.List;

public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final BookService bookService;

    public CustomerService(CustomerRepository customerRepository, AddressService addressService, BookService bookService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
        this.bookService = bookService;
    }

    @Override
    public Customer create(CreateCustomerRequest request) {
        try{
            var customer = CustomerMapper.instance.toEntity(request);
            var adress = addressService.create(request.address());
            customer.setAddress(adress);
            return customerRepository.save(customer);
        } catch (Exception e){
            throw new RuntimeException("Error creating customer");
        }
    }

    @Override
    public Customer update(UpdateCustomerRequest request) {
        try{
             var entity = findByPK(request.cpf());
             request.address().ifPresent((address) -> {
                var updatedAddress = addressService.update(entity.getAddress().getId(), address);
                entity.setAddress(updatedAddress);
             });
             request.firstName().ifPresent(entity::setFirstName);
             request.lastName().ifPresent(entity::setLastName);
             request.telephone().ifPresent(entity::setTelephone);
             request.password().ifPresent(entity::setPassword);
             return entity;
        } catch (Exception e){
            throw new RuntimeException("Error updating customer");
        }
    }

    @Override
    public Customer findByPK(String id) throws EntityNotFoundException {
        return customerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Customer not found"));
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void delete(String id) {
        var entity = findByPK(id);
        try{
            entity.setActive(false);
            customerRepository.save(entity);
        } catch (Exception e){
            throw new RuntimeException("Error deleting customer");
        }
    }

    @Override
    public Review reviewBook(ReviewRequest request) {
        try {
            var entity = findByPK(request.cpf());
            var book = bookService.findByPK(request.bookId());
            var review = new Review();
            review.setCustomer(entity);
            review.setBook(book);
            review.setDescription(request.description());
            review.setRating(request.rating());
            return review;
        } catch (Exception e){
            throw new RuntimeException("Error creating review");
        }
    }
}
