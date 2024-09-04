package br.com.librigate.model.service.actions;

import br.com.librigate.dto.actions.review.ReviewRequest;
import br.com.librigate.dto.actions.review.ReviewResponse;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.repository.ReviewRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.interfaces.IReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.librigate.model.service.actions.factory.ReviewFactory;
import br.com.librigate.model.service.actions.validator.ReviewValidator;

@Service
public class ReviewService implements IReviewService {

    private final CustomerRepository customerRepository;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final ReviewValidator reviewValidator;
    private final ReviewFactory reviewFactory;

    public ReviewService(CustomerRepository customerRepository, BookRepository bookRepository, 
            ReviewRepository reviewRepository, ReviewValidator reviewValidator, ReviewFactory reviewFactory) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.reviewValidator = reviewValidator;
        this.reviewFactory = reviewFactory;
    }

    @Transactional
    @Override
    public ResponseEntity<?> reviewBook(ReviewRequest request) {

        return HandleRequest.handle(() ->{
            
            reviewValidator.validateRentRequest(request);
            var customer = findCustomer(request.cpf());
            var book = findBook(request.bookIsbn());

            var review = reviewFactory.createReview(customer, book, request);
            reviewRepository.save(review);

            var response = toResponse(review);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }

    private Customer findCustomer(String cpf) {
        return customerRepository.findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    private Book findBook(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    private ReviewResponse toResponse(Review review) {
        return new ReviewResponse(
                review.getCustomer().getCpf(),
                review.getBook().getIsbn(),
                review.getDescription(),
                review.getRating()
        );
    }	
}
