package br.com.librigate.model.service.book.factory;

import br.com.librigate.dto.book.bookCopy.CreateBookCopyRequest;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.entity.book.BookCopy;

import org.springframework.stereotype.Component;

@Component
public class BookCopyFactory {

    public BookCopy create(CreateBookCopyRequest request, Book book) {

        var entity = new BookCopy();
        entity.setBook(book);
        entity.setPrice(request.price());
        entity.setRestock(request.restock());
        entity.setStatus("AVAILABLE");
        entity.setCopyNumber(request.copyNumber());

        return entity;
    }
}
