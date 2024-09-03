package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.book.CreateBookRequest;
import br.com.librigate.dto.book.UpdateBookRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.book.Book;
import org.springframework.http.ResponseEntity;

public interface IBookService {

    Book create(CreateBookRequest request);
    Book update(UpdateBookRequest request);
    Book findByPK(String isbn) throws EntityNotFoundException;
    ResponseEntity<?> findAll();
    ResponseEntity<?> getBooksByCategory(String category);
    ResponseEntity<?> getBooksByAuthor(String author);
    ResponseEntity<?> getBookByIsbn(String bookIsbn);
    ResponseEntity<?> getReview(String bookIsbn);
}
