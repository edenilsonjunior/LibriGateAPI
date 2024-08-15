package br.com.librigate.model.service.book;

import br.com.librigate.model.dto.BookDTO;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.service.interfaces.IBookService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class BookService implements IBookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        var entityList = bookRepository.findAll();
        return entityList.stream()
                .filter(b -> b.getCategory().equals(category))
                .toList();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        var entityList = bookRepository.findAll();
        return entityList.stream()
                .filter(b -> b.getAuthorsName().equals(author))
                .toList();
    }

    @Override
    public List<Review> getReview(String bookIsbn) {
        var entityList = bookRepository.findAll();
        return entityList.stream()
                .filter(b -> b.getIsbn().equals(bookIsbn))
                .findFirst().get().getReviews();
    }

    @Override
    public Book create(BookDTO dto) {



        var entity = bookRepository.save();

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
