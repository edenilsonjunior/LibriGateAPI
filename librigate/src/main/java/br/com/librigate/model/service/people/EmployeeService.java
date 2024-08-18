package br.com.librigate.model.service.people;

import br.com.librigate.model.dto.employee.EmployeeRequest;
import br.com.librigate.model.dto.employee.book.NewBookRequest;
import br.com.librigate.model.dto.employee.book.RestockBook;
import br.com.librigate.model.dto.employee.book.RestockBookRequest;
import br.com.librigate.model.dto.employee.book.RestockResponse;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.book.FisicalBookId;
import br.com.librigate.model.entity.people.Address;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.repository.EmployeeRepository;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.repository.RestockRepository;
import br.com.librigate.model.service.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestockRepository restockRepository;

    @Autowired
    private FisicalBookRepository fisicalBookRepository;

    @Override
    public RestockResponse buyNewBook(NewBookRequest request)  {

        var employeeCompletableFuture = CompletableFuture.supplyAsync(() -> employeeRepository.findById(request.employeeCpf()));
        var bookCompletableFuture = CompletableFuture.supplyAsync(() -> bookRepository.findById(request.isbn()));

        try {
            var employee = employeeCompletableFuture.get()
                    .orElseThrow(()-> new NoSuchElementException("Employee not found"));

            var recoveredBook = bookCompletableFuture.get();

            if(recoveredBook.isPresent()){
               throw new RuntimeException("Ja existe um livro com esse ISBN");
            }

            if(request.quantity() <= 0){
                throw new RuntimeException("Quantidade invalida");
            }

            if(request.price() <= 0){
                throw new RuntimeException("Preço invalido");
            }


            var book = new Book();
            book.setIsbn(request.isbn());
            book.setTitle(request.title());
            book.setDescription(request.description());
            book.setPublisher(request.publisher());
            book.setCategory(request.category());
            book.setAuthorsName(request.authorsName());
            book.setEdition(request.edition());
            book.setLaunchDate(request.launchDate());
            bookRepository.save(book);

            var restock = new Restock();
            restock.setEmployee(employee);
            restock.setRestockDate(LocalDate.now());
            restock.setPrice(request.price() * request.quantity());
            restockRepository.save(restock);


            List<FisicalBook> books = new ArrayList<>();
            var fisicalBookId = new FisicalBookId();
            fisicalBookId.setBook(book);


            for(int i = 0; i < request.quantity(); i++){

                var fisicalBook = new FisicalBook();
                fisicalBook.setBookDetails(fisicalBookId);
                fisicalBook.setRestock(restock);
                books.add(fisicalBookRepository.save(fisicalBook));
            }

            restock.setBookList(books);

            restockRepository.save(restock);

            List<RestockBook> restockBooks = new ArrayList<>();
            String isbn = book.getIsbn();
            int quantity = request.quantity();
            double unitValue = request.price();

            restockBooks.add(new RestockBook(isbn, quantity, unitValue));

            return new RestockResponse(restock.getId(), restock.getRestockDate(), employee.getCpf(), restockBooks);

        }catch (Exception ex){
            throw new RuntimeException("Error buying new book");
        }
    }


    @Override
    public RestockResponse restockBook(RestockBookRequest request) {
        return null;
    }

    @Override
    public List<RestockResponse> getRestockHistory() {
        return List.of();
    }

    @Override
    public Employee create(EmployeeRequest request) {

        try{
            var addressCompletableFuture = CompletableFuture.supplyAsync(() -> addressService.create(request.address()));

            Employee employee = new Employee();
            employee.setCpf(request.cpf());
            employee.setFirstName(request.firstName());
            employee.setLastName(request.lastName());
            employee.setBirthDate(request.birthDate());
            employee.setGender(request.gender());
            employee.setTelephone(request.telephone());
            employee.setRole(request.role());
            employee.setPassword(request.password());

            var address = addressCompletableFuture.get();
            employee.setAddress(address);

            return employeeRepository.save(employee);

        }catch(Exception ex){
            throw new RuntimeException("Error creating employee");
        }
    }

    @Override
    public Employee update(EmployeeRequest request) {

        try{
            var employee = employeeRepository
                    .findById(request.cpf())
                    .orElseThrow();

            var addressCompletableFuture = CompletableFuture.supplyAsync(() -> addressService.create(request.address()));

            employee.setFirstName(request.firstName());
            employee.setLastName(request.lastName());
            employee.setBirthDate(request.birthDate());
            employee.setGender(request.gender());
            employee.setTelephone(request.telephone());
            employee.setRole(request.role());
            employee.setPassword(request.password());

            var address = addressCompletableFuture.get();
            employee.setAddress(address);

            return employeeRepository.save(employee);

        }catch(Exception ex){
            throw new RuntimeException("Error creating employee");
        }
    }

    @Override
    public Optional<Employee> findByPK(String id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void delete(String id) {
        try{

            var employee = employeeRepository
                    .findById(id)
                    .orElseThrow();

            employee.setActive(false);

            employeeRepository.save(employee);

        }catch(Exception ex){
            throw new RuntimeException("Error deleting employee");
        }
    }
}
