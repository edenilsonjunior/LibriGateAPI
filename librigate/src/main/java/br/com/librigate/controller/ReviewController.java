package br.com.librigate.controller;

import br.com.librigate.dto.actions.review.ReviewRequest;
import br.com.librigate.model.service.actions.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> reviewBook(@RequestBody ReviewRequest request){
        return reviewService.reviewBook(request);
    }
}
