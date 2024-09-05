package br.com.librigate.model.service.actions.factory;

import org.springframework.stereotype.Component;

import br.com.librigate.dto.actions.review.ReviewRequest;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.entity.people.Customer;

@Component
public class ReviewFactory {

    public Review createReview(Customer customer, Book book, ReviewRequest request) {
        var review = new Review();
        review.setCustomer(customer);
        review.setBook(book);
        review.setDescription(request.description());
        review.setRating(request.rating());

        return review;
    }

}
