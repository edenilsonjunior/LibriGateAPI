package br.com.librigate.model.service.actions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

import br.com.librigate.exception.EntityNotFoundException;
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

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestockRepository restockRepository;

    @Autowired
    private FisicalBookRepository fisicalBookRepository;


    @Override
    public Restock findByPK(Long id) {
        return restockRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Restock not found"));
    }

    @Override
    public RestockResponse buyNewBook(NewBookRequest request) {

        // if (request.quantity() <= 0)
        //     throw new RuntimeException("Quantidade invalida");

        // if (request.price() <= 0)
        //     throw new RuntimeException("Preço invalido");

        // try {

        //     var employee = employeeService.findByPK(request.employeeCpf());
        //     var recoveredBook = bookService.findByPK(request.isbn());

        //     var book = BookMapper.INSTANCE.toEntity(request);
        //     bookRepository.save(book);

        //     var restock = new Restock();
        //     restock.setEmployee(employee);
        //     restock.setRestockDate(LocalDate.now());
        //     restock.setPrice(request.price() * request.quantity());
        //     restockRepository.save(restock);

        //     List<FisicalBook> books = new ArrayList<>();

        //     for (int i = 0; i < request.quantity(); i++) {

        //         var fisicalBook = new FisicalBook();
        //         fisicalBook.setBook(book);
        //         fisicalBook.setRestock(restock);
        //         fisicalBook.setStatus("AVAILABLE");

        //         Long copyNumber = fisicalBookRepository.findMaxCopyNumberByBookIsbn(request.isbn());
        //         fisicalBook.setCopyNumber(copyNumber == null ? 1 : copyNumber + 1);

        //         books.add(fisicalBookRepository.save(fisicalBook));
        //     }

        //     restock.setBookList(books);

        //     restockRepository.save(restock);

        //     List<RestockBook> restockBooks = new ArrayList<>();
        //     String isbn = book.getIsbn();
        //     int quantity = request.quantity();
        //     double unitValue = request.price();

        //     restockBooks.add(new RestockBook(isbn, quantity, unitValue));

        //     return new RestockResponse(restock.getId(), restock.getRestockDate(), employee.getCpf(), restockBooks);

        // } catch (Exception ex) {

        //     System.out.println("\n\n\n\n\n\n " + ex.getMessage() + "\n\n\n\n\n\n");

        //     throw new RuntimeException("Error buying new book");
        // }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public RestockResponse restockBook(RestockBookRequest request) {
        return null;
    }

    @Override
    public List<RestockResponse> getRestockHistory() {
        return List.of();
    }

}
