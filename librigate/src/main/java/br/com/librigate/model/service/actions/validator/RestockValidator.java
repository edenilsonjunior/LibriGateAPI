package br.com.librigate.model.service.actions.validator;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.dto.book.NewBookRequest;
import br.com.librigate.dto.book.RestockBookRequest;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestockValidator {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public void validateNewBook(NewBookRequest request) {

        validate(request.quantity() <= 0, "Invalid quantity", false);
        validate(request.price() <= 0, "Invalid price", false);
        validate(!employeeRepository.existsById(request.employeeCpf()), "This employee does not exist", true);

        validate(bookRepository.existsById(request.isbn()), "This book already exists", false);
    }


    public void validadeRestock(RestockBookRequest request) {

        validate(!employeeRepository.existsById(request.employeeCpf()), "This employee does not exist", true);

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
