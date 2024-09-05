package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.book.bookCopy.CreateBookCopyRequest;
import br.com.librigate.model.entity.book.BookCopy;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBookCopyService {

    List<BookCopy> findAll();
    ResponseEntity<?> findStock();
    ResponseEntity<?> findStockByBook(String isbn);
    BookCopy findById(Long id);
    BookCopy create(CreateBookCopyRequest request);
}
