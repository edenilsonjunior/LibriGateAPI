package br.com.librigate.model.service.actions;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.dto.actions.review.ReviewRequest;
import br.com.librigate.dto.actions.review.ReviewResponse;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.repository.ReviewRepository;
import br.com.librigate.model.service.book.BookService;
import br.com.librigate.model.service.interfaces.IReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService implements IReviewService {

    private final CustomerRepository customerRepository;
    private final BookService bookService;
    private final ReviewRepository reviewRepository;

    public ReviewService(CustomerRepository customerRepository, BookService bookService, ReviewRepository reviewRepository) {
        this.customerRepository = customerRepository;
        this.bookService = bookService;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<?> reviewBook(ReviewRequest request) {
        try {
            var customer = customerRepository.findById(request.cpf())
                    .orElseThrow(()-> new EntityNotFoundException("Customer not found"));

            var book = bookService.findByPK(request.bookId());

            if(request.rating() < 0)
                throw new ValidationException("Rating is negative");

            if(request.description().isEmpty())
                throw new ValidationException("Description is empty");

            var review = new Review();
            review.setCustomer(customer);
            review.setBook(book);
            review.setDescription(request.description());
            review.setRating(request.rating());

            reviewRepository.save(review);

            var response = new ReviewResponse(
                    review.getCustomer().getCpf(),
                    review.getBook().getIsbn(),
                    review.getDescription(),
                    review.getRating()
            );


            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
