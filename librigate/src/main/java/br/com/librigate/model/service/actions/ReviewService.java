package br.com.librigate.model.service.actions;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.customer.review.ReviewRequest;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.service.book.BookService;
import br.com.librigate.model.service.interfaces.IReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewService implements IReviewService {

    private final CustomerRepository customerService;
    private final BookService bookService;

    public ReviewService(CustomerRepository customerService, BookService bookService) {
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<?> reviewBook(ReviewRequest request) {
        try {
            var customer = customerService.findById(request.cpf())
                    .orElseThrow(()-> new EntityNotFoundException("Customer not found"));
            var book = bookService.findByPK(request.bookId());
            var review = new Review();
            review.setCustomer(customer);
            review.setBook(book);
            review.setDescription(review.getDescription());
            review.setRating(review.getRating());
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
