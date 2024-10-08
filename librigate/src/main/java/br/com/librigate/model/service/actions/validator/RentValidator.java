package br.com.librigate.model.service.actions.validator;

import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.repository.BookCopyRepository;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RentValidator {

    private final RentRepository rentRepository;
    private final CustomerRepository customerRepository;
    private final BookCopyRepository bookCopyRepository;

    @Autowired
    public RentValidator(RentRepository rentRepository, CustomerRepository customerRepository, BookCopyRepository bookCopyRepository) {
        this.rentRepository = rentRepository;
        this.customerRepository = customerRepository;
        this.bookCopyRepository = bookCopyRepository;
    }


    public void validateRent(RentRequest request) {

        var customer = customerRepository.findById(request.customerCpf());
        validate(customer.isEmpty(), "This customer does not exist", true);
        validate(!customer.get().isActive(), "This customer is not active", false);

        request.booksIsbn().forEach(isbn -> {
            validate(!bookCopyRepository.existsByIsbn(isbn), "There is no book copy with this ISBN.", true);
            validate(bookCopyRepository.findAllAvailableByIsbn(isbn).isEmpty(), "There is no book copies available for ISBN: " + isbn, false);
        });
    }


    public void validateRenew(Long rentId) {
        var rent = findRent(rentId);

        validate(rent.getStatus().equals("RETURNED"), "Cannot renew a returned book", false);
    }


    public void validateDevolution(Long rentId) {
        var rent = findRent(rentId);

        validate(!rent.getStatus().equals("RENTED"), "Cannot process devolution for a non-rented book", false);
    }


    private void validate(boolean condition, String message, boolean isEntityNotFoundException) {
        if (condition) {
            if (isEntityNotFoundException) {
                throw new EntityNotFoundException(message);
            }
            throw new ValidationException(message);
        }
    }


    private Rent findRent(Long rentId) {
        return rentRepository.findById(rentId)
                .orElseThrow(() -> new EntityNotFoundException("Rent not found"));
    }

}
