package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.EmployeeDTO;
import br.com.librigate.model.dto.RestockDTO;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.people.Employee;

import java.util.List;

public interface IEmployeeService extends IService<Employee, EmployeeDTO, String> {

    Restock buyNewBook(FisicalBook book);
    Restock restockBook(RestockDTO dto);
    List<Restock> getRestockHistory();
}

