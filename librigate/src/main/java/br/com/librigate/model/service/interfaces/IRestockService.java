package br.com.librigate.model.service.interfaces;

import java.util.List;

import br.com.librigate.model.dto.employee.book.NewBookRequest;
import br.com.librigate.model.dto.employee.book.RestockBookRequest;
import br.com.librigate.model.dto.employee.book.RestockResponse;
import br.com.librigate.model.entity.actions.Restock;
import org.springframework.http.ResponseEntity;

public interface IRestockService {

    ResponseEntity<?> findByPK(Long id);
    ResponseEntity<?> buyNewBook(NewBookRequest request);
    ResponseEntity<?> restockBook(RestockBookRequest request);
    ResponseEntity<?> getRestockHistory();
}
