package br.com.librigate.model.service.book;

import br.com.librigate.dto.book.bookCopy.CreateBookCopyRequest;
import br.com.librigate.dto.book.bookCopy.StockResponse;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.repository.BookCopyRepository;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.book.factory.BookCopyFactory;
import br.com.librigate.model.service.interfaces.IBookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyService implements IBookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;
    private final BookCopyFactory bookCopyFactory;
    private final HandleRequest handleRequest;

    @Autowired
    public BookCopyService(BookCopyRepository bookCopyRepository, BookRepository bookRepository, BookCopyFactory bookCopyFactory, HandleRequest handleRequest    ) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookCopyFactory = bookCopyFactory;
        this.bookRepository = bookRepository;
        this.handleRequest = handleRequest;
    }


     
    @Override
    public BookCopy create(CreateBookCopyRequest request) {
        var book = bookRepository.findById(request.isbn())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        var entity = bookCopyFactory.create(request, book);
        return bookCopyRepository.save(entity);
    }


    @Override
    public BookCopy findById(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book copy not found"));
    }

    @Override
    public List<BookCopy> findAll() {
        return bookCopyRepository.findAll();
    }

    @Override
    public ResponseEntity<?> findStock() {

        return handleRequest.handle(() -> {
            var entityList = bookCopyRepository.findAll();

            var stockList = entityList.stream()
                    .map(this::createStockResponse)
                    .distinct()
                    .toList();

            return new ResponseEntity<>(stockList, HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> findStockByBook(String isbn) {

        return handleRequest.handle(() -> {
            var entityList = bookCopyRepository.findAll();

            var stock = entityList.stream()
                    .filter(book -> book.getBook().getIsbn().equals(isbn))
                    .findFirst()
                    .map(this::createStockResponse)
                    .orElseThrow(() -> new EntityNotFoundException("There is no Book copy with this ISBN."));

            return new ResponseEntity<>(stock, HttpStatus.OK);
        });
    }


    private StockResponse createStockResponse(BookCopy copy) {

        return new StockResponse(
                copy.getBook().getIsbn(),
                copy.getBook().getTitle(),
                bookCopyRepository.countAvailables(copy.getBook().getIsbn()),
                bookCopyRepository.countRented(copy.getBook().getIsbn()),
                bookCopyRepository.countSold(copy.getBook().getIsbn())
        );
    }
}
