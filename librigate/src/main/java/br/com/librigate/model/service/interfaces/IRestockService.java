package br.com.librigate.model.service.interfaces;

import java.util.List;

import br.com.librigate.model.dto.employee.book.NewBookRequest;
import br.com.librigate.model.dto.employee.book.RestockBookRequest;
import br.com.librigate.model.dto.employee.book.RestockResponse;

public interface IRestockService {

    RestockResponse buyNewBook(NewBookRequest request);
    RestockResponse restockBook(RestockBookRequest request);
    List<RestockResponse> getRestockHistory();
}
