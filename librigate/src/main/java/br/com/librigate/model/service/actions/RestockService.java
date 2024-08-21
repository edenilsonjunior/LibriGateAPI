package br.com.librigate.model.service.actions;

import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.service.interfaces.IBookService;
import br.com.librigate.model.service.interfaces.IEmployeeService;
import br.com.librigate.model.service.actions.validator.RestockValidator;
import br.com.librigate.model.service.interfaces.IRestockService;
import br.com.librigate.model.service.actions.factory.RestockFactory;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.repository.RestockRepository;
import br.com.librigate.model.dto.employee.book.*;
import br.com.librigate.model.mapper.book.BookMapper;
import br.com.librigate.exception.EntityNotFoundException;

import jakarta.validation.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestockService implements IRestockService {

    private final IEmployeeService employeeService;
    private final IBookService bookService;

    private final RestockFactory restockFactory;
    private final RestockRepository restockRepository;
    private final RestockValidator restockValidator;
    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Autowired
    public RestockService(
            IEmployeeService employeeService,
            IBookService bookService,
            RestockRepository restockRepository,
            RestockFactory restockFactory,
            RestockValidator restockValidator
    ) {
        this.employeeService = employeeService;
        this.bookService = bookService;
        this.restockRepository = restockRepository;
        this.restockFactory = restockFactory;
        this.restockValidator = restockValidator;
    }


    @Override
    public ResponseEntity<?> findByPK(Long id) {

        var restock = restockRepository.findById(id);

        if (restock.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(restock.get(), HttpStatus.OK);
    }


    @Transactional
    @Override
    public ResponseEntity<?> buyNewBook(NewBookRequest request) {

        try {
            restockValidator.validateNewBook(request);

            String isbn = request.isbn();
            int quantity = request.quantity();
            double unitValue = request.price();
            String employeeCpf = request.employeeCpf();

            bookService.create(bookMapper.toCreateBookRequest(request));

            var employee = employeeService.findByPK(employeeCpf);
            var restock = restockFactory.createRestock(employee, quantity * unitValue);

            var restockBooksResponse = restockFactory.createFisicalBooksByIsbn(isbn, quantity, unitValue, restock);
            var restockBooks = List.of(restockBooksResponse);

            var response = new RestockResponse(restock.getId(), restock.getRestockDate(), employeeCpf, restockBooks);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (ValidationException e ) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    @Override
    public ResponseEntity<?> restockBook(RestockBookRequest request) {
        try {
            restockValidator.validadeRestock(request);

            var employee = employeeService.findByPK(request.employeeCpf());

            double totalPrice = request.books()
                    .stream()
                    .mapToDouble(book -> book.unitValue() * book.quantity())
                    .sum();

            var restock = restockFactory.createRestock(employee, totalPrice);

            var restockBookList = CreateRestockBooks(request, restock);

            var response = new RestockResponse(restock.getId(), restock.getRestockDate(), employee.getCpf(), restockBookList);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<?> getRestockHistory() {
        var response = restockRepository.findAll();

        if (response.isEmpty())
            return new ResponseEntity<>(new ArrayList<Restock>(), HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<RestockBook> CreateRestockBooks (RestockBookRequest request, Restock restock){

        var list = new ArrayList<RestockBook>();

        request.books().forEach((requestBook) -> {
            var restockBook = restockFactory
                    .createFisicalBooksByIsbn(
                            requestBook.isbn(),
                            requestBook.quantity(),
                            requestBook.unitValue(),
                            restock);

            list.add(restockBook);
        });

        return list;
    }

}
