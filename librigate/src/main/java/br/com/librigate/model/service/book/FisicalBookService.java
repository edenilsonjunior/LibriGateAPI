package br.com.librigate.model.service.book;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.StockDTO;
import br.com.librigate.model.dto.fisicalBook.CreateFisicalBookRequest;
import br.com.librigate.model.dto.fisicalBook.UpdateFisicalBookRequest;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.service.interfaces.IBuyService;
import br.com.librigate.model.service.interfaces.IFisicalBookService;
import br.com.librigate.model.service.interfaces.IRestockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FisicalBookService implements IFisicalBookService {

    private final FisicalBookRepository fisicalBookRepository;

    private final BookService bookService;
    private final IBuyService buyService;

    @Autowired
    public FisicalBookService(
            FisicalBookRepository fisicalBookRepository,
            BookService bookService,
            IBuyService buyService
    ) {
        this.fisicalBookRepository = fisicalBookRepository;
        this.bookService = bookService;
        this.buyService = buyService;
    }


    @Override
    public FisicalBook create(CreateFisicalBookRequest request) {

        var book = bookService.findByPK(request.isbn());

        var fisicalBook = new FisicalBook();
        fisicalBook.setBook(book);
        fisicalBook.setPrice(request.price());
        fisicalBook.setRestock(request.restock());
        fisicalBook.setStatus("AVAILABLE");
        fisicalBook.setCopyNumber(request.copyNumber());

        return fisicalBookRepository.save(fisicalBook);
    }


    @Override
    public FisicalBook update(UpdateFisicalBookRequest request) {

        var entity = findByPK(request.id());

        request.status().ifPresent(entity::setStatus);
        request.price().ifPresent(entity::setPrice);
        request.buyId().ifPresent((buyId) ->{
            var buy = buyService.findByPK(request.buyId().get());
            entity.setBuy(buy);
        });

        return fisicalBookRepository.save(entity);
    }


    @Override
    public FisicalBook findByPK(Long id) throws EntityNotFoundException {
        return fisicalBookRepository
                .findById(id)
                .orElseThrow(()-> {
                    return new EntityNotFoundException("Fisical Book not found");
                });
    }


    @Override
    public List<FisicalBook> findAll() {
        return fisicalBookRepository.findAll();
    }


    @Override
    public List<StockDTO> getStock() {
        var entityList = fisicalBookRepository.findAll();

        var stockMap = entityList.stream()
                .collect(Collectors.groupingBy(
                        book -> book.getBook().getIsbn(),
                        Collectors.counting()
                ));

        return stockMap.entrySet().stream()
                .map(entry -> {
                    var book = entityList.stream()
                            .filter(b -> b.getBook().getIsbn().equals(entry.getKey()))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Fisical Book not found"));
                    return new StockDTO(
                            entry.getKey(),
                            book.getBook().getTitle(),
                            entry.getValue().intValue()
                    );
                })
                .collect(Collectors.toList());
    }


    @Override
    public StockDTO getStockByBook(String isbn) {
        var entityList = fisicalBookRepository.findAll();

        return entityList.stream()
                .filter(book -> book.getBook().getIsbn().equals(isbn))
                .findFirst()
                .map(book -> new StockDTO(
                        book.getBook().getIsbn(),
                        book.getBook().getTitle(),
                        (int) entityList.stream()
                                .filter(b -> b.getBook().getIsbn().equals(isbn))
                                .count()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Fisical Book not found"));
    }
}
