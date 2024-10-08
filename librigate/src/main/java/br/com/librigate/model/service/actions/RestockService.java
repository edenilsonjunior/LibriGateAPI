package br.com.librigate.model.service.actions;

import br.com.librigate.dto.actions.restock.BookRequest;
import br.com.librigate.dto.actions.restock.BookResponse;
import br.com.librigate.dto.actions.restock.RestockBookRequest;
import br.com.librigate.dto.actions.restock.RestockResponse;
import br.com.librigate.dto.book.NewBookRequest;
import br.com.librigate.dto.book.bookCopy.CreateBookCopyRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.mapper.book.BookMapper;
import br.com.librigate.model.repository.BookCopyRepository;
import br.com.librigate.model.repository.EmployeeRepository;
import br.com.librigate.model.repository.RestockRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.actions.factory.RestockFactory;
import br.com.librigate.model.service.actions.validator.RestockValidator;
import br.com.librigate.model.service.book.BookCopyService;
import br.com.librigate.model.service.interfaces.IBookService;
import br.com.librigate.model.service.interfaces.IRestockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final BookCopyService bookCopyService;
    private final BookCopyRepository bookCopyRepository;
    private final HandleRequest handleRequest;


    @Autowired
    public RestockService(
        IBookService bookService, 
        RestockFactory restockFactory, 
        RestockRepository restockRepository, 
        EmployeeRepository employeeRepository, 
        RestockValidator restockValidator,
        BookCopyService bookCopyService, 
        BookCopyRepository bookCopyRepository,
        HandleRequest handleRequest
    ) {
        this.bookService = bookService;
        this.restockFactory = restockFactory;
        this.restockRepository = restockRepository;
        this.employeeRepository = employeeRepository;
        this.restockValidator = restockValidator;
        this.bookMapper = BookMapper.INSTANCE;
        this.bookCopyService = bookCopyService;
        this.bookCopyRepository = bookCopyRepository;
        this.handleRequest = handleRequest;
    }


    @Override
    public ResponseEntity<?> findAll() {

        return handleRequest.handle(() -> {
            var restocks = restockRepository.findAll();

            var response = restocks.stream()
                    .map(this::toRestockResponse)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> findById(Long id) {

        return handleRequest.handle(() -> {
            var restock = restockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restock not found"));

            var response = toRestockResponse(restock);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

     
    @Override
    public ResponseEntity<?> buyNewBook(NewBookRequest request) {

        return handleRequest.handle(() -> {
            restockValidator.validateNewBook(request);

            var employee = findEmployeeByCPF(request.employeeCpf());
            bookService.create(bookMapper.toCreateBookRequest(request));

            double totalPrice = request.quantity() * request.unityValue();
            var restock = restockFactory.createRestock(employee, totalPrice);
            restockRepository.save(restock);

            var copies = createBookCopiesByIsbn(request.isbn(), request.quantity(),request.unityValue(), restock);
            restock.setBookList(copies);

            var response = toRestockResponse(restock);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }

     
    @Override
    public ResponseEntity<?> restockBook(RestockBookRequest request) {

        return handleRequest.handle(() -> {
            restockValidator.validateRestock(request);

            var employee = findEmployeeByCPF(request.employeeCpf());
            double totalPrice = calculateTotalPrice(request.books());

            var restock = restockFactory.createRestock(employee, totalPrice);
            restockRepository.save(restock);

            var restockBooks = createRestockBooks(request, restock);
            restock.setBookList(restockBooks);
            
            var response = toRestockResponse(restock);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }


    private Employee findEmployeeByCPF(String cpf) throws EntityNotFoundException {
        return employeeRepository
                .findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    private List<BookCopy> createRestockBooks(RestockBookRequest request, Restock restock) {
        var list = new ArrayList<BookCopy>();
        
        for (var requestBook : request.books()) {
            list.addAll(createBookCopiesByIsbn(
                    requestBook.isbn(),
                    requestBook.quantity(),
                    requestBook.unitValue(),
                    restock));
        }
        return list;               
    }

    private List<BookCopy> createBookCopiesByIsbn(String isbn, int quantity, double price, Restock restock) {

        var list = new ArrayList<BookCopy>();

        for (int i = 0; i < quantity; i++) {
            Long copyNumber = bookCopyRepository.findMaxCopyNumberByBookIsbn(isbn);
            copyNumber = copyNumber == null ? 1 : copyNumber + 1;
    
            var createFKRequest = new CreateBookCopyRequest(isbn, copyNumber,price, restock);
            
            var response = bookCopyService.create(createFKRequest);
            list.add(response);
        }

        return list;
    }


    private double calculateTotalPrice(List<BookRequest> books) {
        return books.stream()
                .mapToDouble(book -> book.unitValue() * book.quantity())
                .sum();
    }

    
    private RestockResponse toRestockResponse(Restock restock) {

        var fullName = restock.getEmployee().getFirstName() + " " + restock.getEmployee().getLastName();

        return new RestockResponse(
                restock.getId(),
                restock.getEmployee().getCpf(),
                fullName,
                restock.getPrice(),
                restock.getRestockDate(),
                getRestockBooks(restock));
    }


    private List<BookResponse> getRestockBooks(Restock restock) {
        return restock.getBookList().stream()
                .collect(Collectors.groupingBy(book -> book.getBook().getIsbn()))
                .entrySet().stream()
                .map(entry -> new BookResponse(
                        entry.getKey(),
                        entry.getValue().get(0).getBook().getTitle(),
                        entry.getValue().size(),
                        entry.getValue().get(0).getPrice()))
                .toList();
    }

}
