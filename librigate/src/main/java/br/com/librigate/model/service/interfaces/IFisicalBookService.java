package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.dto.book.fisicalBook.CreateFisicalBookRequest;
import br.com.librigate.dto.book.fisicalBook.UpdateFisicalBookRequest;
import br.com.librigate.model.entity.book.FisicalBook;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFisicalBookService {

    FisicalBook create(CreateFisicalBookRequest request);
    FisicalBook update(UpdateFisicalBookRequest request);
    FisicalBook findByPK(Long id) throws EntityNotFoundException;
    List<FisicalBook> findAll();
    ResponseEntity<?> getStock();
    ResponseEntity<?> getStockByBook(String isbn);
}
