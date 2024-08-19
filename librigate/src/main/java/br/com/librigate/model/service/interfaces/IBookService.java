package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.book.CreateBookRequest;
import br.com.librigate.model.dto.book.UpdateBookRequest;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.book.Book;

import java.util.List;

public interface IBookService {

    Book create(CreateBookRequest request);
    Book update(UpdateBookRequest request);
    Book findByPK(String isbn) throws EntityNotFoundException;
    List<Book> findAll();

    List<Book> getBooks();
    List<Book> getBooksByCategory(String category);
    List<Book> getBooksByAuthor(String author);
    List<Review> getReview(String bookIsbn);
}
