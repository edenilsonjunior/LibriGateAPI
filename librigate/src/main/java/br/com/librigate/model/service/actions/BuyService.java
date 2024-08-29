package br.com.librigate.model.service.actions;

import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.dto.actions.buy.BuyResponse;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.mapper.actions.BuyMapper;
import br.com.librigate.model.repository.BuyRepository;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.actions.factory.BuyFactory;
import br.com.librigate.model.service.actions.validator.BuyValidator;
import br.com.librigate.model.service.interfaces.IBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BuyService implements IBuyService {

    private final BuyRepository buyRepository;
    private final CustomerRepository customerRepository;
    private final FisicalBookRepository fisicalBookRepository;
    private final BuyFactory buyFactory;
    private final BuyValidator buyValidator;
    private final BuyMapper buyMapper = BuyMapper.INSTANCE;

    @Autowired
    public BuyService(BuyRepository buyRepository, CustomerRepository customerRepository, FisicalBookRepository fisicalBookRepository, BuyFactory buyFactory, BuyValidator buyValidator) {
        this.buyRepository = buyRepository;
        this.customerRepository = customerRepository;
        this.fisicalBookRepository = fisicalBookRepository;
        this.buyFactory = buyFactory;
        this.buyValidator = buyValidator;
    }


    @Transactional
    @Override
    public ResponseEntity<?> purchase(BuyRequest request) {

        return HandleRequest.handle(() -> {

            var customer = findCustomerByCPF(request.customerCpf());
            var availableBooks = getAvailableBooks(request);

            var entity = buyFactory.createBuy(request, customer);
            var soldBooks = buyFactory.soldBooks(request, entity, availableBooks);

            entity.setBooks(soldBooks);
            entity.calculateTotalPrice();

            var response = buyMapper.toBuyResponse(entity);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        });
    }


    @Override
    public ResponseEntity<?> processPayment(Long buyId) {

        return HandleRequest.handle(() -> {
            var entity = findBuyById(buyId);

            buyValidator.validatePayment(entity);
            buyFactory.approvePayment(entity);

            var response = buyMapper.toBuyResponse(entity);

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }


    @Override
    public ResponseEntity<?> cancelPurchase(Long buyId) {
        return HandleRequest.handle(() -> {
            var entity = findBuyById(buyId);

            buyValidator.validatePaymentCancel(entity);

            entity.setStatus("CANCELED");
            buyRepository.save(entity);

            restoreBooks(entity.getBooks());

            var response = new BuyResponse(
                    entity.getId(),
                    entity.getCustomer().getCpf(),
                    entity.getTotalPrice(),
                    entity.getBuyDate(),
                    entity.getDueDate(),
                    Optional.ofNullable(entity.getPaidAt()),
                    entity.getStatus()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> getPurchases(String cpf) {

        return HandleRequest.handle(() -> {

            var entities = buyRepository.findByCustomerCpf(cpf);
            if (entities.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(entities, HttpStatus.OK);

        });
    }

    @Override
    public ResponseEntity<?> getPurchaseById(String cpf, Long paymentId) {

        return HandleRequest.handle(() -> {

            var entity = buyRepository.findByCustomerCpfAndId(cpf, paymentId);
            if (entity.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<?> findByPK(Long id) {
        return HandleRequest.handle(() -> {
            var entity = buyRepository.findById(id);
            if (entity.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        });
    }


    private Map<String, List<FisicalBook>> getAvailableBooks(BuyRequest request) {

        var map = new HashMap<String, List<FisicalBook>>();

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

        return map;
    }


    private void restoreBooks(List<FisicalBook> books) {

        for (var book : books) {
            book.setStatus("AVAILABLE");
            book.setBuy(null);
            fisicalBookRepository.save(book);
        }
    }


    private Customer findCustomerByCPF(String cpf) throws EntityNotFoundException {
        return customerRepository
                .findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }


    private Buy findBuyById(Long id) {

        return buyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Buy not found"));
    }
}
