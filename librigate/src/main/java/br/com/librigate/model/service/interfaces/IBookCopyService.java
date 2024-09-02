package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.dto.book.bookCopy.CreateBookCopyRequest;
import br.com.librigate.dto.book.bookCopy.UpdateBookCopyRequest;
import br.com.librigate.model.entity.book.BookCopy;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBookCopyService {

    BookCopy create(CreateBookCopyRequest request);
    BookCopy update(UpdateBookCopyRequest request);
    BookCopy findByPK(Long id) throws EntityNotFoundException;
    List<BookCopy> findAll();
    ResponseEntity<?> getStock();
    ResponseEntity<?> getStockByBook(String isbn);
}
