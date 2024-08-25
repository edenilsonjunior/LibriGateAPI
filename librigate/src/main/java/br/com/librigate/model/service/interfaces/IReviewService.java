package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.actions.review.ReviewRequest;
import org.springframework.http.ResponseEntity;

public interface IReviewService {
    ResponseEntity<?> reviewBook(ReviewRequest request);
}
