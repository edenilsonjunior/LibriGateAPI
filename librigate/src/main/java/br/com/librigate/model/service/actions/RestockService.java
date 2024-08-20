package br.com.librigate.model.service.actions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.fisicalBook.CreateFisicalBookRequest;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.mapper.book.BookMapper;
import br.com.librigate.model.service.book.FisicalBookService;
import br.com.librigate.model.service.interfaces.IBookService;
import br.com.librigate.model.service.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.librigate.model.dto.employee.book.NewBookRequest;
import br.com.librigate.model.dto.employee.book.RestockBook;
import br.com.librigate.model.dto.employee.book.RestockBookRequest;
import br.com.librigate.model.dto.employee.book.RestockResponse;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.repository.RestockRepository;
import br.com.librigate.model.service.interfaces.IRestockService;
import br.com.librigate.model.service.people.EmployeeService;
import br.com.librigate.model.service.book.BookService;
import org.springframework.stereotype.Service;

@Service
public class RestockService implements IRestockService {

    private final IEmployeeService employeeService;
    private final IBookService bookService;
    private final FisicalBookService fisicalBookService;

    private final BookRepository bookRepository;
    private final RestockRepository restockRepository;
    private final FisicalBookRepository fisicalBookRepository;

    @Autowired
    public RestockService(IEmployeeService employeeService, IBookService bookService, FisicalBookService fisicalBookService, BookRepository bookRepository, RestockRepository restockRepository, FisicalBookRepository fisicalBookRepository) {
        this.employeeService = employeeService;
        this.bookService = bookService;
        this.fisicalBookService = fisicalBookService;
        this.bookRepository = bookRepository;
        this.restockRepository = restockRepository;
        this.fisicalBookRepository = fisicalBookRepository;
    }


    @Override
    public Restock findByPK(Long id) {
        return restockRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Restock not found"));
    }


    @Override
    public RestockResponse buyNewBook(NewBookRequest request) {

        String isbn = request.isbn();
        int quantity = request.quantity();
        double unitValue = request.price();

        try {
            validate(quantity <= 0, "Invalid quantity");
            validate(unitValue <= 0, "Invalid price");
            validate(bookRepository.existsById(isbn), "This book already exists");

            var employee = employeeService.findByPK(request.employeeCpf());

            var createBookRequest = BookMapper.INSTANCE.toCreateBookRequest(request);
            var book = bookService.create(createBookRequest);

            var restock = create(employee, quantity  * unitValue);

            var fisicalBooks = new ArrayList<FisicalBook>(request.quantity());
            for (int i = 0; i < request.quantity(); i++) {

                Long copyNumber = fisicalBookRepository.findMaxCopyNumberByBookIsbn(request.isbn());
                copyNumber = copyNumber == null ? 1 : copyNumber + 1;

                var createFKRequest = new CreateFisicalBookRequest(book.getIsbn(), copyNumber, request.price(), restock);

                var response = fisicalBookService.create(createFKRequest);
                fisicalBooks.add(response);
            }

            restock.setBookList(fisicalBooks);
            restockRepository.save(restock);

            var restockBooks = new ArrayList<RestockBook>();
            restockBooks.add(new RestockBook(isbn, quantity, unitValue));

            return new RestockResponse(restock.getId(), restock.getRestockDate(), employee.getCpf(), restockBooks);

        } catch (Exception ex) {

            System.out.println("\n\n\n\n\n\n " + ex.getMessage() + "\n\n\n\n\n\n");

            throw new RuntimeException("Error buying new book");
        }
    }


    @Override
    public RestockResponse restockBook(RestockBookRequest request) {

        var employee = employeeService.findByPK(request.employeeCpf());

        double totalPrice = request.books()
                .stream()
                .mapToDouble(book -> book.unitValue() * book.quantity())
                .sum();

        var restock = create(employee, totalPrice);

        var fisicalBooks = new ArrayList<FisicalBook>();

        request.books().forEach((requestBook)->{
            for (int i = 0; i < requestBook.quantity(); i++) {

                Long copyNumber = fisicalBookRepository.findMaxCopyNumberByBookIsbn(requestBook.isbn());
                copyNumber = copyNumber == null ? 1 : copyNumber + 1;

                var createFKRequest = new CreateFisicalBookRequest(requestBook.isbn(), copyNumber, requestBook.unitValue(), restock);

                var response = fisicalBookService.create(createFKRequest);
                fisicalBooks.add(response);
            }
        });

        restock.setBookList(fisicalBooks);
        restockRepository.save(restock);

        var restockBooks = new ArrayList<RestockBook>();

        request.books().forEach((requestBook)->{
            restockBooks.add(new RestockBook(requestBook.isbn(), requestBook.quantity(), requestBook.unitValue()));
        });

        return new RestockResponse(restock.getId(), restock.getRestockDate(), employee.getCpf(), restockBooks);
    }


    @Override
    public List<RestockResponse> getRestockHistory() {
        return List.of();
    }


    private Restock create(Employee employee, double price){

        var restock = new Restock();
        restock.setEmployee(employee);
        restock.setRestockDate(LocalDate.now());
        restock.setPrice(price);
        return restockRepository.save(restock);
    }


    private void validate(boolean condition, String message) {
        if (condition) {
            throw new RuntimeException(message);
        }
    }

}
