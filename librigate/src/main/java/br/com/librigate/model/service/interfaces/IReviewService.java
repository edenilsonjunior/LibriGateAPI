package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.customer.review.ReviewRequest;
import org.springframework.http.ResponseEntity;

public interface IReviewService {
    ResponseEntity<?> reviewBook(ReviewRequest request);
}
