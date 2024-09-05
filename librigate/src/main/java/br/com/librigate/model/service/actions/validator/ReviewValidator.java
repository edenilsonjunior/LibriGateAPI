package br.com.librigate.model.service.actions.validator;

import br.com.librigate.dto.actions.review.ReviewRequest;
import br.com.librigate.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ReviewValidator {
    
    public void validateRentRequest(ReviewRequest request) {
        if(request.rating() < 0)
            throw new ValidationException("Rating is negative");

        if(request.description().isEmpty())
            throw new ValidationException("Description is empty");
    }


}
