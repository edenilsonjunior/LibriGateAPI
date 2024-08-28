package br.com.librigate.model.service.interfaces;


import br.com.librigate.dto.book.NewBookRequest;
import br.com.librigate.dto.actions.restock.RestockBookRequest;

import org.springframework.http.ResponseEntity;

public interface IRestockService {

    ResponseEntity<?> findByPK(Long id);
    ResponseEntity<?> buyNewBook(NewBookRequest request);
    ResponseEntity<?> restockBook(RestockBookRequest request);
    ResponseEntity<?> getRestockHistory();
}
