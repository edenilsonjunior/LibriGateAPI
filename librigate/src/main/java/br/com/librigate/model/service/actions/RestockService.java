package br.com.librigate.model.service.actions;

import br.com.librigate.dto.actions.restock.RestockBook;
import br.com.librigate.dto.actions.restock.RestockBookRequest;
import br.com.librigate.dto.actions.restock.RestockResponse;
import br.com.librigate.dto.book.NewBookRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.mapper.book.BookMapper;
import br.com.librigate.model.repository.EmployeeRepository;
import br.com.librigate.model.repository.RestockRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.actions.factory.RestockFactory;
import br.com.librigate.model.service.actions.validator.RestockValidator;
import br.com.librigate.model.service.interfaces.IBookService;
import br.com.librigate.model.service.interfaces.IRestockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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


    @Transactional
    @Override
    public ResponseEntity<?> buyNewBook(NewBookRequest request) {

        return HandleRequest.handle(() -> {
            restockValidator.validateNewBook(request);

            var employee = findEmployeeByCPF(request.employeeCpf());
            bookService.create(bookMapper.toCreateBookRequest(request));

            var restock = restockFactory.createRestock(employee, (request.quantity() * request.unityValue()));

            var restockBooksResponse = restockFactory.createBookCopiesByIsbn(request.isbn(), request.quantity(), request.unityValue(), restock);

            var response = new RestockResponse(
                    restock.getId(),
                    restock.getPrice(),
                    restock.getRestockDate(),
                    request.employeeCpf(),
                    List.of(restockBooksResponse)
            );

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }


    @Transactional
    @Override
    public ResponseEntity<?> restockBook(RestockBookRequest request) {

        return HandleRequest.handle(() -> {
            restockValidator.validadeRestock(request);

            var employee = findEmployeeByCPF(request.employeeCpf());
            double totalPrice = calculateTotalPrice(request.books());

            var restock = restockFactory.createRestock(employee, totalPrice);
            var restockBookList = createRestockBooks(request, restock);

            var response = new RestockResponse(
                    restock.getId(),
                    restock.getPrice(),
                    restock.getRestockDate(),
                    employee.getCpf(),
                    restockBookList);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }


    @Transactional
    @Override
    public ResponseEntity<?> findAll() {

        return HandleRequest.handle(() -> {
            var restocks = restockRepository.findAll();

            if (restocks.isEmpty())
                return new ResponseEntity<>(new ArrayList<RestockResponse>(), HttpStatus.NO_CONTENT);

            var response = restocks.stream()
                    .map(this::mapToRestockResponse)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }


    @Transactional
    @Override
    public ResponseEntity<?> findById(Long id) {

        return HandleRequest.handle(() -> {
            var restock = restockRepository.findById(id);

            if (restock.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            var response = new RestockResponse(
                    restock.get().getId(),
                    restock.get().getPrice(),
                    restock.get().getRestockDate(),
                    restock.get().getEmployee().getCpf(),
                    getRestockBooks(restock.get()));

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }


    private Employee findEmployeeByCPF(String cpf) throws EntityNotFoundException {
        return employeeRepository
                .findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }


    private RestockResponse mapToRestockResponse(Restock restock) {
        return new RestockResponse(
                restock.getId(),
                restock.getPrice(),
                restock.getRestockDate(),
                restock.getEmployee().getCpf(),
                getRestockBooks(restock)
        );
    }


    private List<RestockBook> createRestockBooks(RestockBookRequest request, Restock restock) {
        return request.books().stream()
                .map(requestBook -> restockFactory.createBookCopiesByIsbn(
                        requestBook.isbn(),
                        requestBook.quantity(),
                        requestBook.unitValue(),
                        restock))
                .collect(Collectors.toList());
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


    private double calculateTotalPrice(List<RestockBook> books) {
        return books.stream()
                .mapToDouble(book -> book.unitValue() * book.quantity())
                .sum();
    }

}
