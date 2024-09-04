package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.actions.restock.RestockBookRequest;
import br.com.librigate.dto.book.NewBookRequest;
import org.springframework.http.ResponseEntity;

public interface IRestockService {

    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> buyNewBook(NewBookRequest request);
    ResponseEntity<?> restockBook(RestockBookRequest request);
    
}
