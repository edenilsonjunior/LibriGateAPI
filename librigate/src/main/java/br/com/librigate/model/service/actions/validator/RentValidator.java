package br.com.librigate.model.service.actions.validator;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentValidator {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FisicalBookRepository fisicalBookRepository;


    public void validateRent(RentRequest request) {
        validate(!customerRepository.existsById(request.customerCpf()), "Customer does not exist", true);

        request.booksIsbn().forEach(isbn -> {
            validate(!fisicalBookRepository.existsById(isbn), "This book does not exist", true);
            validate(fisicalBookRepository.findAvailableByIsbn(isbn).isEmpty(), "This book is not available for rent", false);
        });
    }

    public void validateRenew(Long rentId) {
        var rent = rentRepository.findById(rentId)
                .orElseThrow(() -> new EntityNotFoundException("Rent not found"));

        validate(rent.getStatus().equals("RETURNED"), "Cannot renew a returned book", false);
    }

    public void validateDevolution(Long rentId) {
        var rent = rentRepository.findById(rentId)
                .orElseThrow(() -> new EntityNotFoundException("Rent not found"));

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
}
