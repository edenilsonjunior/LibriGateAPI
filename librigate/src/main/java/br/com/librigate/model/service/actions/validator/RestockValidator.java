package br.com.librigate.model.service.actions.validator;

import br.com.librigate.dto.actions.restock.RestockBookRequest;
import br.com.librigate.dto.book.NewBookRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RestockValidator {

    private final BookRepository bookRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public RestockValidator(BookRepository bookRepository, EmployeeRepository employeeRepository) {
        this.bookRepository = bookRepository;
        this.employeeRepository = employeeRepository;
    }

    public void validateNewBook(NewBookRequest request) {

        validate(request.launchDate().isAfter(LocalDate.now()), "Invalid date", false);
        validate(request.edition() <= 0, "Invalid edition", false);
        validate(request.quantity() <= 0, "Invalid quantity", false);
        validate(request.unityValue() <= 0, "Invalid price", false);

        var employee = employeeRepository.findById(request.employeeCpf());
        validate(employee.isEmpty(), "This employee does not exist", true);
        validate(!employee.get().isActive(), "This employee is not active", false);

        validate(bookRepository.existsById(request.isbn()), "This book already exists", false);
    }


    public void validateRestock(RestockBookRequest request) {

        var employee = employeeRepository.findById(request.employeeCpf());
        validate(employee.isEmpty(), "This employee does not exist", true);
        validate(!employee.get().isActive(), "This employee is not active", false);

        request.books().forEach((book)-> {
            validate(book.quantity() <= 0, "Invalid quantity", false);
            validate(book.unitValue() <= 0, "Invalid price", false);
            validate(!bookRepository.existsById(book.isbn()), "This book does not exist", true);
        });
    }


    private void validate(boolean condition, String message, boolean isEntityNotFoundException) {
        if (condition) {

            if (isEntityNotFoundException) {
                throw new EntityNotFoundException(message);
            }

            throw new ValidationException(message);
        }
    }
}
