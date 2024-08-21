package br.com.librigate.model.service.book;

import br.com.librigate.model.dto.book.CreateBookRequest;
import br.com.librigate.model.dto.book.UpdateBookRequest;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.mapper.book.BookMapper;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.service.interfaces.IBookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public Book create(CreateBookRequest request) {

        var entity = bookMapper.toEntity(request);
        return bookRepository.save(entity);
    }


    @Override
    public Book update(UpdateBookRequest request) {

        var entity = findByPK(request.isbn());

        request.title().ifPresent(entity::setTitle);
        request.description().ifPresent(entity::setDescription);
        request.publisher().ifPresent(entity::setPublisher);
        request.category().ifPresent(entity::setCategory);
        request.authorsName().ifPresent(entity::setAuthorsName);
        request.edition().ifPresent(entity::setEdition);
        request.launchDate().ifPresent(entity::setLaunchDate);

        return bookRepository.save(entity);
    }


    @Override
    public Book findByPK(String isbn) {
        return bookRepository
            .findById(isbn)
            .orElseThrow(() -> {
                return new EntityNotFoundException("Book not found");
            });
    }


    @Override
    public List<Book> findAll() {
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
}
