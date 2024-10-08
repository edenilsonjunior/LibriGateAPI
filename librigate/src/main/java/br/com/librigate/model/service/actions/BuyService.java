package br.com.librigate.model.service.actions;

import br.com.librigate.dto.actions.buy.BuyBook;
import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.dto.actions.buy.BuyResponse;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.repository.BookCopyRepository;
import br.com.librigate.model.repository.BuyRepository;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.actions.factory.BuyFactory;
import br.com.librigate.model.service.actions.validator.BuyValidator;
import br.com.librigate.model.service.interfaces.IBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class BuyService implements IBuyService {

    private final BuyRepository buyRepository;
    private final CustomerRepository customerRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BuyFactory buyFactory;
    private final BuyValidator buyValidator;
    private final HandleRequest handleRequest;

    @Autowired
    public BuyService(BuyRepository buyRepository, CustomerRepository customerRepository, 
        BookCopyRepository bookCopyRepository, BuyFactory buyFactory, BuyValidator buyValidator, HandleRequest handleRequest
    ) {
        this.buyRepository = buyRepository;
        this.customerRepository = customerRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.buyFactory = buyFactory;
        this.buyValidator = buyValidator;
        this.handleRequest = handleRequest;
    }

     
    @Override
    public ResponseEntity<?> purchase(BuyRequest request) {
        return handleRequest.handle(() -> {

            var customer = findCustomerByCPF(request.customerCpf());
            var availableBooks = getAvailableBooks(request);

            var entity = buyFactory.createBuy(request, customer);
            buyRepository.save(entity);

            var soldBooks = buyFactory.soldBooks(request, entity, availableBooks);
            bookCopyRepository.saveAll(soldBooks);

            entity.setBooks(soldBooks);
            entity.calculateTotalPrice();

            var response = toBuyResponse(entity);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }

    @Override
    public ResponseEntity<?> processPayment(Long buyId) {
        return handleRequest.handle(() -> {

            var entity = findBuyById(buyId);
            buyValidator.validatePayment(entity);

            entity.setStatus("APPROVED");
            entity.setPaidAt(LocalDateTime.now());
            buyRepository.save(entity);

            var response = toBuyResponse(entity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> cancelPurchase(Long buyId) {
        return handleRequest.handle(() -> {

            var entity = findBuyById(buyId);
            buyValidator.validatePaymentCancel(entity);

            entity.setStatus("CANCELED");
            buyRepository.save(entity);
            restoreBooks(entity.getBooks());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        });
    }

    @Override
    public ResponseEntity<?> findPurchasesByCustomerCpf(String cpf) {
        return handleRequest.handle(() -> {

            var entities = buyRepository.findByCustomerCpf(cpf);
            var response = entities.stream()
                    .map(this::toBuyResponse).toList();

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        return handleRequest.handle(() -> {

            var entity = buyRepository.findById(id);
            if (entity.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            var response = toBuyResponse(entity.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    private BuyResponse toBuyResponse(Buy buy) {

        var customer =  buy.getCustomer();
        String fullName = customer.getFirstName() + " " + customer.getLastName();

        var copiesGroupedByIsbn = buy.getBooks().stream()
                .collect(Collectors.groupingBy(copy -> copy.getBook().getIsbn()));

        var buyBooks = copiesGroupedByIsbn
                .entrySet()
                .stream()
                .map(group -> new BuyBook(
                        group.getKey(),
                        group.getValue().get(0).getBook().getTitle(),
                        group.getValue().size()))
                .toList();

        return new BuyResponse(
                buy.getId(),
                buy.getCustomer().getCpf(),
                fullName,
                buy.getBuyDate(),
                buy.getDueDate(),
                Optional.ofNullable(buy.getPaidAt()),
                buy.getTotalPrice(),
                buy.getStatus(),
                buyBooks);
    }

    private Map<String, List<BookCopy>> getAvailableBooks(BuyRequest request) {

        var map = new HashMap<String, List<BookCopy>>();

        request.books().forEach(buyBook -> {

            var bookCopies = bookCopyRepository.findAllAvailableByIsbn(buyBook.isbn());

            if (bookCopies.size() < buyBook.quantity())
                throw new EntityNotFoundException("Not enough books in stock for ISBN: " + buyBook.isbn());

            map.put(buyBook.isbn(), bookCopies.subList(0, buyBook.quantity()));
        });

        return map;
    }

    private void restoreBooks(List<BookCopy> books) {

        books.forEach(book -> {
            book.setStatus("AVAILABLE");
            book.setBuy(null);
            bookCopyRepository.save(book);
        });
    }

    private Customer findCustomerByCPF(String cpf) throws EntityNotFoundException {
        var customer = customerRepository.findById(cpf);

        if(customer.isEmpty())
            throw new EntityNotFoundException("Customer not found");

        if(!customer.get().isActive())
            throw new EntityNotFoundException("Customer is not active");

        return customer.get();
    }

    private Buy findBuyById(Long id) {
        return buyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Buy not found"));
    }

}
