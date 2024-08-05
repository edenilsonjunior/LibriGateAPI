package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.BookDTO;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.book.Book;

import java.util.List;

public interface IBookService extends IService<Book, BookDTO, String> {

    List<Book> getBooks();
    List<Book> getBooksByCategory(String category);
    List<Book> getBooksByAuthor(String author);

    List<Review> getReview(String bookIsbn);
}
