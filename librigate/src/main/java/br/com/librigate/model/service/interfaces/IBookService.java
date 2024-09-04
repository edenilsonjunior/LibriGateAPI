package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.book.CreateBookRequest;
import br.com.librigate.dto.book.UpdateBookRequest;
import br.com.librigate.model.entity.book.Book;
import org.springframework.http.ResponseEntity;

public interface IBookService {

    ResponseEntity<?> findAll();
    ResponseEntity<?> findBooksByCategory(String category);
    ResponseEntity<?> findBooksByAuthor(String author);
    ResponseEntity<?> findBookByIsbn(String bookIsbn);
    ResponseEntity<?> findReview(String bookIsbn);
    Book create(CreateBookRequest request);
    ResponseEntity<?> update(UpdateBookRequest request);
}
