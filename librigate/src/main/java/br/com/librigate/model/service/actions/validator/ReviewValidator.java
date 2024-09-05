package br.com.librigate.model.service.actions.validator;

import br.com.librigate.dto.actions.review.ReviewRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewValidator {

    private final CustomerRepository customerRepository;

    @Autowired
    public ReviewValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public void validateRentRequest(ReviewRequest request) {

        var customer = customerRepository.findById(request.customerCpf());
        validate(customer.isEmpty(), "This customer does not exist", true);
        validate(!customer.get().isActive(), "This customer is not active", false);

        if(request.rating() < 0)
            throw new ValidationException("Rating is negative");

        if(request.description().isEmpty())
            throw new ValidationException("Description is empty");
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
