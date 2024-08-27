package br.com.librigate.model.service.actions;

import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.dto.actions.buy.BuyResponse;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.repository.BuyRepository;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.service.interfaces.IBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BuyService implements IBuyService {

    private final BuyRepository buyRepository;
    private final CustomerRepository customerRepository;
    private final FisicalBookRepository fisicalBookRepository;

    @Autowired
    public BuyService(BuyRepository buyRepository, CustomerRepository customerRepository, FisicalBookRepository fisicalBookRepository) {
        this.buyRepository = buyRepository;
        this.customerRepository = customerRepository;
        this.fisicalBookRepository = fisicalBookRepository;
    }

    @Override
    public ResponseEntity<?> findByPK(Long id) {
        var entity = buyRepository.findById(id);
        if (entity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity.get(), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> purchase(BuyRequest request) {
        try {
            var customer = customerRepository.findById(request.customerCpf())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

            Map<String, List<FisicalBook>> map = new HashMap<>();
            List<FisicalBook> soldedBooks = new ArrayList<>();

            request.books().forEach(buyBook -> {

                var fisicalBooks = fisicalBookRepository
                        .findAllByIsbn(buyBook.isbn())
                        .stream()
                        .filter(fisicalBook -> fisicalBook.getStatus().equals("AVAILABLE"))
                        .toList();

                if (fisicalBooks.size() < buyBook.quantity())
                    throw new EntityNotFoundException("Not enough books in stock");

                map.put(buyBook.isbn(), fisicalBooks);
            });

            var entity = new Buy();
            entity.setCustomer(customer);
            entity.setBooks(soldedBooks);
            entity.setBuyDate(LocalDateTime.now());
            entity.setDueDate(LocalDate.now().plusDays(2));
            entity.setStatus("PENDING");

            buyRepository.save(entity);


            request.books().forEach(buyBook -> {

                var books = map.get(buyBook.isbn());

                for (int i = 0; i < buyBook.quantity(); i++) {
                    var book = books.get(i);
                    book.setStatus("SOLD");
                    book.setBuy(entity);
                    fisicalBookRepository.save(book);
                    soldedBooks.add(book);
                }
            });

            entity.calculateTotalPrice();
            var response = new BuyResponse(
                    entity.getId(),
                    entity.getCustomer().getCpf(),
                    String.valueOf(entity.getTotalPrice()),
                    entity.getBuyDate(),
                    entity.getDueDate(),
                    Optional.ofNullable(entity.getPaidAt()),
                    entity.getStatus()
            );


            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> processPayment(Long buyId) {
        try {
            var entity = buyRepository.findById(buyId)
                    .orElseThrow(() -> new EntityNotFoundException("Buy not found"));

            if (entity.getStatus().equals("APPROVED"))
                throw new ValidationException("Payment already processed");

            if (entity.getStatus().equals("CANCELED"))
                throw new ValidationException("Purchase already canceled");

            entity.setStatus("APPROVED");
            entity.setPaidAt(LocalDateTime.now());
            buyRepository.save(entity);

            var response = new BuyResponse(
                    entity.getId(),
                    entity.getCustomer().getCpf(),
                    String.valueOf(entity.getTotalPrice()),
                    entity.getBuyDate(),
                    entity.getDueDate(),
                    Optional.ofNullable(entity.getPaidAt()),
                    entity.getStatus()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> cancelPurchase(Long buyId) {
        try {
            var entity = buyRepository.findById(buyId)
                    .orElseThrow(() -> new EntityNotFoundException("Buy not found"));

            if (entity.getStatus().equals("CANCELED")) {
                throw new ValidationException("Purchase already canceled");
            }

            if(entity.getStatus().equals("APPROVED")){
                throw new ValidationException("Purchase already approved");
            }

            entity.setStatus("CANCELED");
            buyRepository.save(entity);


            for (var book : entity.getBooks()) {
                book.setStatus("AVAILABLE");
                book.setBuy(null);
                fisicalBookRepository.save(book);
            }

            var response = new BuyResponse(
                    entity.getId(),
                    entity.getCustomer().getCpf(),
                    String.valueOf(entity.getTotalPrice()),
                    entity.getBuyDate(),
                    entity.getDueDate(),
                    Optional.ofNullable(entity.getPaidAt()),
                    entity.getStatus()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getPurchases(String cpf) {
        var entities = buyRepository.findByCustomerCpf(cpf);
        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPurchaseById(String cpf, Long paymentId) {
        var entity = buyRepository.findByCustomerCpfAndId(cpf, paymentId);
        if (entity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity.get(), HttpStatus.OK);
    }
}
