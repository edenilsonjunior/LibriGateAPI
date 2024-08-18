package br.com.librigate.model.service.book;

import br.com.librigate.model.dto.FisicalBookDTO;
import br.com.librigate.model.dto.StockDTO;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.book.FisicalBookId;
import br.com.librigate.model.mapper.book.FisicalBookMapper;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.repository.RestockRepository;
import br.com.librigate.model.service.interfaces.IFisicalBookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FisicalBookService implements IFisicalBookService {

    @Autowired
    private FisicalBookMapper fisicalBookMapper;

    @Autowired
    private FisicalBookRepository fisicalBookRepository;

    @Autowired
    private RestockRepository restockRepository;

    @Override
    public List<StockDTO> getStock() {
        var entityList = fisicalBookRepository.findAll();

        var stockMap = entityList.stream()
                .collect(Collectors.groupingBy(
                        book -> book.getBookDetails().getBook().getIsbn(),
                        Collectors.counting()
                ));

        return stockMap.entrySet().stream()
                .map(entry -> {
                    var book = entityList.stream()
                            .filter(b -> b.getBookDetails().getBook().getIsbn().equals(entry.getKey()))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Fisical Book not found"));
                    return new StockDTO(
                            entry.getKey(),
                            book.getBookDetails().getBook().getTitle(),
                            entry.getValue().intValue()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public StockDTO getStockByBook(String isbn) {
        var entityList = fisicalBookRepository.findAll();

        return entityList.stream()
                .filter(book -> book.getBookDetails().getBook().getIsbn().equals(isbn))
                .findFirst()
                .map(book -> new StockDTO(
                        book.getBookDetails().getBook().getIsbn(),
                        book.getBookDetails().getBook().getTitle(),
                        (int) entityList.stream()
                                .filter(b -> b.getBookDetails().getBook().getIsbn().equals(isbn))
                                .count()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Fisical Book not found"));
    }

    @Override
    public FisicalBook create(FisicalBookDTO dto) {
        var entity = fisicalBookMapper.toEntity(dto);

        entity.setRestock(restockRepository.findById(dto.restockId())
                .orElseThrow(() -> new EntityNotFoundException("Restock not found")));

        return fisicalBookRepository.save(entity);
    }

    @Override
    public FisicalBook update(FisicalBookDTO dto) {
        var fisicalBookId = fisicalBookMapper.toFisicalBookId(dto);

        var entity = fisicalBookRepository.findById(fisicalBookId)
                .orElseThrow(() -> new EntityNotFoundException("Fisical Book not found"));

        entity.setPrice(dto.price());
        entity.setStatus(dto.status());

        return fisicalBookRepository.save(entity);
    }

    @Override
    public Optional<FisicalBook> findByPK(FisicalBookId id) {
        return fisicalBookRepository.findById(id);
    }

    @Override
    public List<FisicalBook> findAll() {
        return fisicalBookRepository.findAll();
    }

    @Override
    public void delete(FisicalBookId id) {
        var entity = fisicalBookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fisical Book not found"));

        fisicalBookRepository.delete(entity);
    }
}
