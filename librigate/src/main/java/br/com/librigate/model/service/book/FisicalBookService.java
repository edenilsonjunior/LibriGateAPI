package br.com.librigate.model.service.book;

import br.com.librigate.dto.book.fisicalBook.CreateFisicalBookRequest;
import br.com.librigate.dto.book.fisicalBook.StockResponse;
import br.com.librigate.dto.book.fisicalBook.UpdateFisicalBookRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.repository.BuyRepository;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.service.interfaces.IBookService;
import br.com.librigate.model.service.interfaces.IFisicalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FisicalBookService implements IFisicalBookService {

    private final FisicalBookRepository fisicalBookRepository;

    private final IBookService bookService;

    private final BuyRepository buyRepository;

    @Autowired
    public FisicalBookService(
            FisicalBookRepository fisicalBookRepository,
            IBookService bookService,
            BuyRepository buyRepository
    ) {
        this.fisicalBookRepository = fisicalBookRepository;
        this.bookService = bookService;
        this.buyRepository = buyRepository;
    }


    @Transactional
    @Override
    public FisicalBook create(CreateFisicalBookRequest request) {
        var entityBook = bookService.findByPK(request.isbn());

        var entityFisicalBook = new FisicalBook();
        entityFisicalBook.setBook(entityBook);
        entityFisicalBook.setPrice(request.price());
        entityFisicalBook.setRestock(request.restock());
        entityFisicalBook.setStatus("AVAILABLE");
        entityFisicalBook.setCopyNumber(request.copyNumber());

        return fisicalBookRepository.save(entityFisicalBook);
    }


    @Transactional
    @Override
    public FisicalBook update(UpdateFisicalBookRequest request) {
        var entity = fisicalBookRepository.findById(request.id())
            .orElseThrow(() -> new EntityNotFoundException("Fisical Book not found"));

        request.status().ifPresent(entity::setStatus);
        request.price().ifPresent(entity::setPrice);
        request.buyId().ifPresent((buyId) ->{

            var buy = buyRepository.findById(buyId)
                .orElseThrow(() -> new EntityNotFoundException("Buy not found"));

            entity.setBuy(buy);
        });

        return fisicalBookRepository.save(entity);
    }


    @Override
    public FisicalBook findByPK(Long id) {
        return fisicalBookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fisical Book not found"));
    }


    @Override
    public List<FisicalBook> findAll() {
        return fisicalBookRepository.findAll();
    }


    @Override
    public ResponseEntity<?> getStock() {
        try {
            var entityList = fisicalBookRepository.findAll();

            var stockList = entityList.stream()
                    .map(fisicalBook -> new StockResponse(
                            fisicalBook.getBook().getIsbn(),
                            fisicalBook.getBook().getTitle(),
                            fisicalBookRepository.countByIsbn(fisicalBook
                                    .getBook()
                                    .getIsbn())
                    ))
                    .distinct()
                    .toList();

            if (stockList.isEmpty())
                return new ResponseEntity<>(stockList, HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(stockList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<?> getStockByBook(String isbn) {
        try {
            var entityList = fisicalBookRepository.findAll();

            var stock = entityList.stream()
                    .filter(book -> book.getBook().getIsbn().equals(isbn))
                    .findFirst()
                    .map(fisicalBook -> new StockResponse(
                            fisicalBook.getBook().getIsbn(),
                            fisicalBook.getBook().getTitle(),
                            fisicalBookRepository.countByIsbn(fisicalBook
                                    .getBook()
                                    .getIsbn())
                    ))
                    .orElseThrow(() -> new EntityNotFoundException("Fisical Book not found"));

            return new ResponseEntity<>(stock, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
