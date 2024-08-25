package br.com.librigate.model.service.actions;

import br.com.librigate.exception.ValidationException;
import br.com.librigate.dto.book.NewBookRequest;
import br.com.librigate.dto.book.RestockBook;
import br.com.librigate.dto.book.RestockBookRequest;
import br.com.librigate.dto.book.RestockResponse;
import br.com.librigate.model.repository.EmployeeRepository;
import br.com.librigate.model.service.interfaces.IBookService;
import br.com.librigate.model.service.actions.validator.RestockValidator;
import br.com.librigate.model.service.interfaces.IRestockService;
import br.com.librigate.model.service.actions.factory.RestockFactory;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.repository.RestockRepository;
import br.com.librigate.model.mapper.book.BookMapper;
import br.com.librigate.exception.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestockService implements IRestockService {

    private final IBookService bookService;

    private final RestockFactory restockFactory;

    private final RestockRepository restockRepository;

    private final EmployeeRepository employeeRepository;

    private final RestockValidator restockValidator;

    private final BookMapper bookMapper;

    @Autowired
    public RestockService(
            IBookService bookService,
            RestockFactory restockFactory,
            RestockRepository restockRepository,
            EmployeeRepository employeeRepository,
            RestockValidator restockValidator
    ) {
        this.bookService = bookService;
        this.restockFactory = restockFactory;
        this.restockRepository = restockRepository;
        this.employeeRepository = employeeRepository;
        this.restockValidator = restockValidator;
        this.bookMapper = BookMapper.INSTANCE;
    }


    @Override
    public ResponseEntity<?> findByPK(Long id) {

        var restock = restockRepository.findById(id);

        if (restock.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var response = new RestockResponse(
                restock.get().getId(),
                Optional.of(restock.get().getPrice()),
                restock.get().getRestockDate(),
                restock.get().getEmployee().getCpf(),
                getRestockBooks(restock.get())
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
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

            var employee = employeeRepository.findById(employeeCpf)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

            var restock = restockFactory.createRestock(employee, quantity * unitValue);

            var restockBooksResponse = restockFactory.createFisicalBooksByIsbn(isbn, quantity, unitValue, restock);
            var restockBooks = List.of(restockBooksResponse);

            var response = new RestockResponse(restock.getId(), Optional.of(restock.getPrice()), restock.getRestockDate(), employeeCpf, restockBooks);

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

            var employee = employeeRepository.findById(request.employeeCpf())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

            double totalPrice = request.books()
                    .stream()
                    .mapToDouble(book -> book.unitValue() * book.quantity())
                    .sum();

            var restock = restockFactory.createRestock(employee, totalPrice);

            var restockBookList = createRestockBooks(request, restock);

            var response = new RestockResponse(restock.getId(), Optional.of(restock.getPrice()), restock.getRestockDate(), employee.getCpf(), restockBookList);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<?> getRestockHistory() {
        var restocks = restockRepository.findAll();

        if (restocks.isEmpty())
            return new ResponseEntity<>(new ArrayList<RestockResponse>(), HttpStatus.NO_CONTENT);

        var responses = restocks.stream()
                .map(restock -> {
                    return new RestockResponse(
                            restock.getId(),
                            Optional.of(restock.getPrice()),
                            restock.getRestockDate(),
                            restock.getEmployee().getCpf(),
                            getRestockBooks(restock)
                    );
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


    private List<RestockBook> createRestockBooks(RestockBookRequest request, Restock restock){

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


    private List<RestockBook> getRestockBooks(Restock restock) {
        return restock.getBookList().stream()
                .collect(Collectors.groupingBy(book -> book.getBook().getIsbn()))
                .entrySet().stream()
                .map(entry -> new RestockBook(
                        entry.getKey(),
                        entry.getValue().size(),
                        entry.getValue().get(0).getPrice()
                ))
                .toList();
    }
}
