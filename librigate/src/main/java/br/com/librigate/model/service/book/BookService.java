package br.com.librigate.model.service.book;

import br.com.librigate.dto.actions.review.ReviewResponse;
import br.com.librigate.dto.book.CreateBookRequest;
import br.com.librigate.dto.book.UpdateBookRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.mapper.book.BookMapper;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.book.factory.BookFactory;
import br.com.librigate.model.service.interfaces.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final BookFactory bookFactory;
    private final BookMapper bookMapper =BookMapper.INSTANCE;
    private final HandleRequest handleRequest;

    @Autowired
    public BookService(BookRepository bookRepository, BookFactory bookFactory, HandleRequest handleRequest
    ) {
        this.bookRepository = bookRepository;
        this.bookFactory = bookFactory;
        this.handleRequest = handleRequest;
    }


    @Override
    public ResponseEntity<?> findAll() {
        return handleRequest.handle(()-> {
            var entityList = bookRepository.findAll();

            var response = entityList.stream()
                    .map(bookMapper::toResponse).toList();

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> findBooksByCategory(String category) {

        return handleRequest.handle(()->{
            var entityList = bookRepository.findAll();

            var filteredEntityList = entityList.stream()
                    .filter(b -> b.getCategory().toLowerCase()
                            .contains(category.toLowerCase()))
                    .map(bookMapper::toResponse)
                    .toList();

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> findBooksByAuthor(String author) {

        return handleRequest.handle(()->{
            var entityList = bookRepository.findAll();

            var filteredEntityList = entityList.stream()
                    .filter(b -> b.getAuthorsName().stream()
                            .anyMatch(fb -> fb.toLowerCase().contains(author.toLowerCase())))
                    .map(bookMapper::toResponse)
                    .toList();

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> findBookByIsbn(String bookIsbn) {

        return handleRequest.handle(() -> {
            var entityList = bookRepository.findAll();

            var filteredEntityList = entityList.stream()
                    .filter(b -> b.getIsbn().equals(bookIsbn))
                    .findFirst()
                    .map(bookMapper::toResponse)
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));

            return new ResponseEntity<>(filteredEntityList, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> findReview(String bookIsbn) {

        return handleRequest.handle(() -> {

            var entityList = bookRepository.findById(bookIsbn)
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));


            var reviews = entityList.getReviews().stream()
                    .map(r -> new ReviewResponse(
                            r.getCustomer().getCpf(),
                            r.getBook().getIsbn(),
                            r.getDescription(),
                            r.getRating()
                    ))
                    .toList();

            return new ResponseEntity<>(reviews, HttpStatus.OK);
        });
    }

     
    @Override
    public Book create(CreateBookRequest request) {
        var entity = bookMapper.toEntity(request);
        return bookRepository.save(entity);
    }


     
    @Override
    public ResponseEntity<?> update(UpdateBookRequest request) {

        return handleRequest.handle(()->{
            var entity = bookRepository.findById(request.isbn())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));

            bookFactory.update(request, entity);
            bookRepository.save(entity);

            var response = bookMapper.toResponse(entity);

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }
}
