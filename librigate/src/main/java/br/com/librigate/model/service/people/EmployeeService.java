package br.com.librigate.model.service.people;

import br.com.librigate.model.dto.EmployeeDTO;
import br.com.librigate.model.dto.RestockDTO;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.service.interfaces.IEmployeeService;

import java.util.List;
import java.util.Optional;

public class EmployeeService implements IEmployeeService {
    @Override
    public Restock buyNewBook(FisicalBook book) {
        return null;
    }

    @Override
    public Restock restockBook(RestockDTO dto) {
        return null;
    }

    @Override
    public List<Restock> getRestockHistory() {
        return null;
    }

    @Override
    public Employee create(EmployeeDTO dto) {
        return null;
    }

    @Override
    public Employee update(String id, EmployeeDTO dto) {
        return null;
    }

    @Override
    public Optional<Employee> findByPK(String id) {
        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
