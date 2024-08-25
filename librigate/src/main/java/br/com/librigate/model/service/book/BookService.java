package br.com.librigate.model.service.book;

import br.com.librigate.dto.book.CreateBookRequest;
import br.com.librigate.dto.book.UpdateBookRequest;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.mapper.book.BookMapper;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.service.interfaces.IBookService;
import br.com.librigate.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Autowired
    public BookService(
            BookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
        this.bookMapper = BookMapper.INSTANCE;
    }


    @Transactional
    @Override
    public Book create(CreateBookRequest request) {
        var entity = bookMapper.toEntity(request);
        return bookRepository.save(entity);
    }


    @Transactional
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

            if (filteredEntityList.isEmpty())
                return new ResponseEntity<>(filteredEntityList, HttpStatus.NOT_FOUND);

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
                    .filter(b -> b.getAuthorsName().contains(author))
                    .toList();

            if (filteredEntityList.isEmpty())
                return new ResponseEntity<>(filteredEntityList, HttpStatus.NOT_FOUND);

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

            if (filteredEntityList.isEmpty())
                return new ResponseEntity<>(filteredEntityList, HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
