package br.com.librigate.controller;

import br.com.librigate.model.dto.customer.review.ReviewRequest;
import br.com.librigate.model.service.actions.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/review")
public class ReviewController {

    ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> reviewBook(@RequestBody ReviewRequest request){
        return reviewService.reviewBook(request);
    }
}
