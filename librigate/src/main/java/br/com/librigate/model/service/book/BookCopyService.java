package br.com.librigate.model.service.book;

import br.com.librigate.dto.book.bookCopy.CreateBookCopyRequest;
import br.com.librigate.dto.book.bookCopy.StockResponse;
import br.com.librigate.dto.book.bookCopy.UpdateBookCopyRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.repository.BuyRepository;
import br.com.librigate.model.repository.BookCopyRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.interfaces.IBookService;
import br.com.librigate.model.service.interfaces.IBookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookCopyService implements IBookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final IBookService bookService;
    private final BuyRepository buyRepository;

    @Autowired
    public BookCopyService(BookCopyRepository bookCopyRepository, IBookService bookService, BuyRepository buyRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookService = bookService;
        this.buyRepository = buyRepository;
    }


    @Transactional
    @Override
    public BookCopy create(CreateBookCopyRequest request) {
        var entityBook = bookService.findByPK(request.isbn());

        var entity = new BookCopy();
        entity.setBook(entityBook);
        entity.setPrice(request.price());
        entity.setRestock(request.restock());
        entity.setStatus("AVAILABLE");
        entity.setCopyNumber(request.copyNumber());

        return bookCopyRepository.save(entity);
    }


    @Transactional
    @Override
    public BookCopy update(UpdateBookCopyRequest request) {
        var entity = bookCopyRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Book copy not found"));

        request.status().ifPresent(entity::setStatus);
        request.price().ifPresent(entity::setPrice);
        request.buyId().ifPresent((buyId) -> {

            var buy = buyRepository.findById(buyId)
                    .orElseThrow(() -> new EntityNotFoundException("Buy not found"));

            entity.setBuy(buy);
        });

        return bookCopyRepository.save(entity);
    }


    @Override
    public BookCopy findByPK(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book copy not found"));
    }


    @Override
    public List<BookCopy> findAll() {
        return bookCopyRepository.findAll();
    }


    @Override
    public ResponseEntity<?> getStock() {

        return HandleRequest.handle(() -> {
            var entityList = bookCopyRepository.findAll();

            if (entityList.isEmpty())
                return new ResponseEntity<>(entityList, HttpStatus.NO_CONTENT);

            var stockList = entityList.stream()
                    .map(this::createStockResponse)
                    .distinct()
                    .toList();

            return new ResponseEntity<>(stockList, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> getStockByBook(String isbn) {
        return HandleRequest.handle(() -> {
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
                bookCopyRepository.countByIsbn(copy.getBook().getIsbn())
        );
    }

}
