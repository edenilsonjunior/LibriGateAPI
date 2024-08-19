package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.StockDTO;
import br.com.librigate.model.dto.fisicalBook.CreateFisicalBookRequest;
import br.com.librigate.model.dto.fisicalBook.UpdateFisicalBookRequest;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.mapper.book.FisicalBookMapper;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.repository.RestockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public FisicalBook create(CreateFisicalBookRequest request) {
        // var entity = fisicalBookMapper.toEntity(dto);

        // entity.setRestock(restockRepository.findById(dto.restockId())
        //         .orElseThrow(() -> new EntityNotFoundException("Restock not found")));
        // return fisicalBookRepository.save(entity);
        throw new UnsupportedOperationException("Not implemented yet");
    }


    @Override
    public FisicalBook update(UpdateFisicalBookRequest request) {

        // var entity = fisicalBookRepository.findById(dto.id())
        //         .orElseThrow(() -> new EntityNotFoundException("Fisical Book not found"));

        // entity.setPrice(dto.price());
        // entity.setStatus(dto.status());

        // return fisicalBookRepository.save(entity);
        throw new UnsupportedOperationException("Not implemented yet");
    }


    @Override
    public FisicalBook findByPK(Long id) throws EntityNotFoundException{
        return fisicalBookRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Fisical Book not found"));
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
