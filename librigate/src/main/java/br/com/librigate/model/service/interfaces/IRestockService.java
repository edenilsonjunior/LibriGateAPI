package br.com.librigate.model.service.interfaces;

import java.util.List;

import br.com.librigate.model.dto.employee.book.NewBookRequest;
import br.com.librigate.model.dto.employee.book.RestockBookRequest;
import br.com.librigate.model.dto.employee.book.RestockResponse;
import br.com.librigate.model.entity.actions.Restock;

public interface IRestockService {

    Restock findByPK(Long id);

    RestockResponse buyNewBook(NewBookRequest request);
    RestockResponse restockBook(RestockBookRequest request);
    List<RestockResponse> getRestockHistory();
}
