package br.com.librigate.model.service.book;

import br.com.librigate.model.dto.BookDTO;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.service.interfaces.IBookService;

import java.util.List;
import java.util.Optional;

public class BookService implements IBookService {
    @Override
    public List<Book> getBooks() {
        return null;
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return null;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return null;
    }

    @Override
    public List<Review> getReview(String bookIsbn) {
        return null;
    }

    @Override
    public Book create(BookDTO dto) {
        return null;
    }

    @Override
    public Book update(String id, BookDTO dto) {
        return null;
    }

    @Override
    public Optional<Book> findByPK(String id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
