package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.BookDTO;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.service.interfaces.IService;

import java.util.List;

public interface IBookService extends IService<Book, BookDTO, String> {

    public List<Book> getBooks();
    public List<Book> getBooksByCategory(String category);
    public List<Book> getBooksByAuthor(String author);

    public List<Review> getReview(String bookIsbn);
}
