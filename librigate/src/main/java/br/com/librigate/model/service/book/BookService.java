package br.com.librigate.model.service.book;

import br.com.librigate.dto.actions.review.ReviewResponse;
import br.com.librigate.dto.book.BookGettersResponse;
import br.com.librigate.dto.book.CreateBookRequest;
import br.com.librigate.dto.book.UpdateBookRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.mapper.book.BookMapper;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.interfaces.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository) {
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
    public ResponseEntity<?> findAll() {
        return HandleRequest.handle(()-> {
            var entityList = bookRepository.findAll();

            var response = entityList.stream()
                    .map(this::toBookGettersResponse).toList();

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> getBooksByCategory(String category) {

        return HandleRequest.handle(()->{
            var entityList = bookRepository.findAll();

            var filteredEntityList = entityList.stream()
                    .filter(b -> b.getCategory().toLowerCase()
                            .contains(category.toLowerCase()))
                    .map(this::toBookGettersResponse)
                    .toList();

            if (filteredEntityList.isEmpty())
                return new ResponseEntity<>(filteredEntityList, HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> getBooksByAuthor(String author) {
        return HandleRequest.handle(()->{
            var entityList = bookRepository.findAll();

            var filteredEntityList = entityList.stream()
                    .filter(b -> b.getAuthorsName().stream()
                            .anyMatch(fb -> fb.toLowerCase().contains(author.toLowerCase())))
                    .map(this::toBookGettersResponse)
                    .toList();

            if (filteredEntityList.isEmpty())
                return new ResponseEntity<>(filteredEntityList, HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> getBookByIsbn(String bookIsbn) {

        return HandleRequest.handle(() -> {
            var entityList = bookRepository.findAll();

            var filteredEntityList = entityList.stream()
                    .filter(b -> b.getIsbn().equals(bookIsbn))
                    .findFirst()
                    .map(this::toBookGettersResponse)
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> getReview(String bookIsbn) {

        return HandleRequest.handle(() -> {

            var entityList = bookRepository.findAll();

            var filteredEntityList = entityList.stream()
                    .filter(b -> b.getIsbn().equals(bookIsbn))
                    .findFirst()
                    .map(b -> b.getReviews().stream()
                            .map(r -> new ReviewResponse(
                                    r.getCustomer().getCpf(),
                                    r.getBook().getIsbn(),
                                    r.getDescription(),
                                    r.getRating()
                            ))
                    )
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"))
                    .toList();

            if (filteredEntityList.isEmpty())
                return new ResponseEntity<>(filteredEntityList, HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        });
    }

    private BookGettersResponse toBookGettersResponse(Book b) {

        return new BookGettersResponse(
                b.getIsbn(),
                b.getTitle(),
                b.getDescription(),
                b.getPublisher(),
                b.getCategory(),
                b.getAuthorsName(),
                b.getEdition(),
                b.getLaunchDate());
    }
}
