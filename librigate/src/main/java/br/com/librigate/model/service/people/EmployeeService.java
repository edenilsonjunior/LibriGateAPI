package br.com.librigate.model.service.people;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import br.com.librigate.model.dto.EmployeeDTO;
import br.com.librigate.model.dto.RestockDTO;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.people.Address;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.repository.EmployeeRepository;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.repository.RestockRepository;
import br.com.librigate.model.service.interfaces.IEmployeeService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeService implements IEmployeeService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RestockRepository restockRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FisicalBookRepository fisicalBookRepository;


    @Override
    public Restock buyNewBook(FisicalBook book) throws Exception {

        // Verificando se o Book existe

        boolean contains = bookRepository.existsById(book.getBookDetails().getBook().getIsbn());

        if(!contains)
            throw new Exception();

        throw new NotImplementedException();
    }


    @Override
    public Restock restockBook(RestockDTO dto) throws Exception {

        Employee employee = employeeRepository.findById(dto.employeeCpf()).orElse(null);

        if(employee == null)
            return null;

        List<FisicalBook> books = new ArrayList<>();

        for (var b : dto.books()){

            var book = bookRepository.findById(b.isbn()).orElse(null);

            if(book == null)
                throw new Exception("Book with isbn: " + b.isbn() + " not found");

            for (int i = 0; i < b.quantity(); i++){

                FisicalBook fisicalBook = new FisicalBook();

                fisicalBook.getBookDetails().setBook(book);

                //TODO: Verificar se a posicao do price deve estar em book ou fisicalBook
            }
        }

        return null;
    }

    @Override
    public List<Restock> getRestockHistory(){
        return restockRepository.findAll();
    }

    @Override
    public Employee create(EmployeeDTO dto) {

        Address address = addressService.create(dto.address());

        Employee employee = new Employee();
        employee.setCpf(dto.cpf());
        employee.setFirstName(dto.firstName());
        employee.setLastName(dto.lastName());
        employee.setBirthDate(dto.birthDate());
        employee.setGender(dto.gender());
        employee.setTelephone(dto.telephone());
        employee.setLogin(dto.cpf());
        employee.setPassword(dto.password());
        employee.setAddress(address);

        employee.setRestockList(new ArrayList<>());
        employee.setRole(dto.role());

        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(String id, EmployeeDTO dto) {

        var employee = findByPK(id).orElse(null);

        if(employee == null)
            return null;

        Address address = addressService.create(dto.address());

        employee.setFirstName(dto.firstName());
        employee.setLastName(dto.lastName());
        employee.setBirthDate(dto.birthDate());
        employee.setGender(dto.gender());
        employee.setTelephone(dto.telephone());
        employee.setRole(dto.role());
        employee.setPassword(dto.password());
        employee.setAddress(address);

        return employeeRepository.save(employee);
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
        employeeRepository.deleteById(id);
    }
}
