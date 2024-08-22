package br.com.librigate.model.service.book;

import br.com.librigate.model.dto.book.CreateBookRequest;
import br.com.librigate.model.dto.book.UpdateBookRequest;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.mapper.book.BookMapper;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.service.interfaces.IBookService;
import br.com.librigate.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        var entity = bookRepository.findById(request.isbn())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

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
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }


    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }


    @Override
    public ResponseEntity<?> getBooksByCategory(String category) {
        try {
            var entityList = bookRepository.findAll();

            var filteredEntityList = entityList.stream()
                    .filter(b -> b.getCategory().equals(category))
                    .toList();

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<?> getBooksByAuthor(String author) {
        try {
            var entityList = bookRepository.findAll();

            var filteredEntityList = entityList.stream()
                    .filter(b -> b.getAuthorsName().equals(author))
                    .toList();

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<?> getReview(String bookIsbn) {
        try {
            var entityList = bookRepository.findAll();

            var filteredEntityList = entityList.stream()
                    .filter(b -> b.getIsbn().equals(bookIsbn))
                    .findFirst().get().getReviews();

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
