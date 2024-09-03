package br.com.librigate.model.service.actions;

import br.com.librigate.dto.actions.rent.RentBook;
import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.dto.actions.rent.RentResponse;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.repository.BookCopyRepository;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.repository.RentRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.actions.factory.RentFactory;
import br.com.librigate.model.service.actions.validator.RentValidator;
import br.com.librigate.model.service.interfaces.IRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RentService implements IRentService {

    private final CustomerRepository customerRepository;
    private final BookCopyRepository bookCopyRepository;
    private final RentRepository rentRepository;
    private final RentValidator rentValidator;
    private final RentFactory rentFactory;

    @Autowired
    public RentService(CustomerRepository customerRepository, BookCopyRepository bookCopyRepository,
            RentRepository rentRepository, RentValidator rentValidator, RentFactory rentFactory) {
        this.customerRepository = customerRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.rentRepository = rentRepository;
        this.rentValidator = rentValidator;
        this.rentFactory = rentFactory;
    }

    @Transactional
    @Override
    public ResponseEntity<?> rent(RentRequest request) {

        return HandleRequest.handle(() -> {
            rentValidator.validateRent(request);

            var customer = findCustomerByCpf(request.customerCpf());
            var listBookIsbn = request.booksIsbn().stream().distinct().toList();

            var availableBooks = rentFactory.getAvailableBooksForRent(listBookIsbn);
            var rent = rentFactory.createRent(customer);

            var books = rentFactory.associateBooksToRent(availableBooks, rent);
            bookCopyRepository.saveAll(books);
            rent.setBookList(books);

            rentRepository.save(rent);
            var response = toRentResponse(rent);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }


    @Transactional
    @Override
    public ResponseEntity<?> processDevolutionBook(Long rentId) {
        return HandleRequest.handle(() -> {
            rentValidator.validateDevolution(rentId);

            var rent = findById(rentId);

            rent.setStatus("RETURNED");
            rent.setGivenBackAt(LocalDateTime.now());
            rentRepository.save(rent);

            restoreBooks(rent);

            var response = toRentResponse(rent);

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }


    @Transactional
    @Override
    public ResponseEntity<?> renewRent(Long rentId) {
        return HandleRequest.handle(() -> {

            rentValidator.validateRenew(rentId);

            var rent = findById(rentId);
            rent.setDevolutionDate(rent.getDevolutionDate().plusWeeks(1));
            rentRepository.save(rent);

            var response = toRentResponse(rent);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> getRents(String cpf) {
        return HandleRequest.handle(() -> {

            var list = rentRepository.findAllByCustomerCpf(cpf);

            var response = list.stream()
                    .map(this::toRentResponse)
                    .toList();

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> getRendById(Long rentId) {
        return HandleRequest.handle(() -> {
            var rent = findById(rentId);
            var response = toRentResponse(rent);

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    private Rent findById(Long rentId) {
        return rentRepository.findById(rentId)
                .orElseThrow(() -> new EntityNotFoundException("Rent not found"));
    }

    private void restoreBooks(Rent rent) {
        rent.getBookList().forEach(book -> {
            book.setStatus("AVAILABLE");
            bookCopyRepository.save(book);
        });
    }

    private RentResponse toRentResponse(Rent rent) {

        var books = rent.getBookList()
                .stream()
                .map(book ->{

                    var isbn = book.getBook().getIsbn();
                    var copyNumber = book.getCopyNumber();
                    var title = book.getBook().getTitle();
                    var status = book.getStatus();

                    return new RentBook(isbn, copyNumber, title, status);

                }).distinct().toList();

        return new RentResponse(
                rent.getId(),
                rent.getCustomer().getCpf(),
                rent.getRentDate(),
                rent.getStatus(),
                rent.getDevolutionDate(),
                Optional.ofNullable(rent.getGivenBackAt()),
                books);
    }

    private Customer findCustomerByCpf(String cpf){
        return customerRepository.findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

}
